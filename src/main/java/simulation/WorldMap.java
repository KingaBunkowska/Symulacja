package simulation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WorldMap extends AbstractWorldMap {
    private final static String STATS_FILE = "stats.json";

    private int animalEnergy;
    private int plantEnergy;
    private int dayNumber = 1;
    private int noOfPlants, noOfAnimals;

    private List<Animal> animals = new ArrayList<>();
    private final Map<Vector2D, List<Animal>> animalsPositions = new HashMap<>();
    private final Map<Vector2D, Plant> plants = new HashMap<>();
    private final Random random = new Random();

    private SimulationStatistics statistics;

    public WorldMap (){
        super(SimulationParams.getField("width"),SimulationParams.getField("height"));
    }

    public void setSimulation(){
        this.width=SimulationParams.getField("width");
        this.height=SimulationParams.getField("height");
        this.animalEnergy = SimulationParams.getField("animalEnergy");
        this.plantEnergy=SimulationParams.getField("plantEnergy");
        this.noOfPlants=SimulationParams.getField("noOfPlants");
        //this.noOfAnimals=SimulationParams.getField("noOfAnimals");
        animals.clear();
        plants.clear();
        for (int i=0;i<SimulationParams.getField("noOfAnimals");i++){
            Animal animal= new Animal(getRandomPosition(),animalEnergy);
            animals.add(animal);
            List<Animal> animalsAtPosition = animalsPositions.get(animal.getPosition());
            placeAnimalOnMap(animal);
        }
        for (int i=0; i<noOfPlants;i++){
            if (plants.size() > getHeight() * getWidth()) break;
            placePlantOnMap();
        }
    }

    private void placePlantOnMap (){
        Vector2D position = getRandomPosition();
        while (isOccupiedByPlant(position) || plants.size() > getWidth() * getHeight()) position = getRandomPosition();
        plants.put(position,new Plant (position));
    }

    private boolean isOccupiedByPlant(Vector2D position){
        return getPlantAtPosition(position) != null;
    }

    private Plant getPlantAtPosition(Vector2D position){
        return plants.get(position);
    }

    private Vector2D getRandomPosition (){
        return new Vector2D(random.nextInt(getWidth()), random.nextInt(getHeight()));
    }

    private void placeAnimalOnMap(Animal animal) {
        animalsPositions.computeIfAbsent(animal.getPosition(), pos -> new LinkedList<>()).add(animal); //zwraca linkedListę i dodaje zwierzatko
    }

    @Override
    public void run (){
        System.out.println("Today is day: " + dayNumber);
        animalsPositions.clear();
        animals.forEach(animal -> {
            animal.moveBasedOnGenome();
            placeAnimalOnMap(animal);
        });
    }

    @Override
    public void fight(){
        animalsPositions.forEach((position, animals) -> {
            List <Animal> fighters = animals.stream()
                    .filter(animal -> animal.getEnergy()> animalEnergy/2)
                    .filter(animal -> animal.isMale())
                    .sorted(Collections.reverseOrder())
                    .limit(2)
                    .collect(Collectors.toList());
            if (fighters.size()==2){
                fighters.get(0).setEnergy(fighters.get(0).getEnergy()-10);
                fighters.get(1).setEnergy(fighters.get(1).getEnergy()-20);
                System.out.println("Animal "+fighters.get(0).getAnimalId()+" was fighting with animal "+fighters.get(1).getAnimalId()+" on position "+position);
            }
        });
    }

    @Override
    public void reproduce(){
        List<Animal> children = new LinkedList<>();
        animalsPositions.forEach((position, animals) -> {
                List <Animal> parents = animals.stream()
                        .filter(animal -> animal.getEnergy()>animalEnergy/2)
                        .sorted(Collections.reverseOrder())
                        .limit(2)
                        .collect(Collectors.toList());
                if (parents.size()==2 && parents.get(0).isMale()!=parents.get(1).isMale()){
                    if (parents.get(0).isMale()){
                        Collections.swap(parents,0,1);
                    }
                    Animal child = new Animal(parents.get(0),parents.get(1));
                    System.out.println("Animal "+child.getAnimalId()+" was born on position "+position);
                    children.add(child);
                }
        });
    children.forEach(this::addNewAnimal);
    }

    private void addNewAnimal(Animal animal){
        animals.add(animal);
        placeAnimalOnMap(animal);
    }

    @Override
    public void atTheEndOfDay(){
        dayNumber++;
        animals = animals.stream()
                .map(Animal::aging)//musi zwracac zwierze
                .map(animal -> animal.setEnergy(animal.getEnergy()-5))
                .filter (animal -> animal.getEnergy()>0)
                .collect(Collectors.toList());
        //mapa z miejscem i tak jest na poczatku dnia sie clearuje
        createStatistics();
    }

    private void createStatistics(){
        statistics = new SimulationStatistics(
                dayNumber,
                animals.stream().mapToInt(Animal::getAge).average().orElse(0),
                animals.stream().mapToInt(Animal::getNumberOfChildren).average().orElse(0),
                animals.stream().mapToInt(Animal::getEnergy).average().orElse(0),
                animals.size(),
                plants.size()
        );
        System.out.println(statistics);
        JsonParser.dumpStatisticsToJsonFile(STATS_FILE, statistics);
    }

    public SimulationStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(SimulationStatistics statistics) {
        this.statistics = statistics;
    }

    /*public void eat(){
        animals.forEach(animal -> {
            if (isOccupiedByPlant(animal.getPosition())){
                animal.setEnergy(animal.getEnergy()+PLANT_ENERGY);
                plants.remove(animal.getPosition());
                placePlantOnMap();
                System.out.println("Animal ate plant on position" + animal.getPosition());
            }
        });*/

    private void eatPlant (Animal animal) {
        animal.setEnergy(animal.getEnergy()+plantEnergy);
        plants.remove(animal.getPosition());
        //System.out.println("Animal ate plant on position" + animal.getPosition());
    }

    public void eat(){
        animalsPositions.forEach((position, animals) -> {
            if (isOccupiedByPlant(position)){
                animals.stream()
                        .max(Animal::compareTo)
                        .ifPresent(animal -> eatPlant(animal));
            }
        });
        if (plants.size() < getWidth() * getHeight() - plants.size()/10 - 1)
        IntStream.range(1, new Random().nextInt(noOfPlants/10)+1).forEach(i -> placePlantOnMap());
    }

    @Override
    public Map<Vector2D, Plant> getPlantsLocations() {
        return plants;
    }

    @Override
    public Map<Vector2D, List<Animal>> getAnimalLocations() {
        return animalsPositions;
    }


}

