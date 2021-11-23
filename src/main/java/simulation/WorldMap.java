package simulation;

import java.util.*;
import java.util.stream.Collectors;

public class WorldMap extends AbstractWorldMap{

    private List<Animal> animals = new ArrayList<>();
    private static final int INITIAL_ENERGY=20;
    private static final int ENERGY_LOST=10;
    private static final int PLANT_ENERGY=10;
    private HashMap<Vector2D, LinkedList<Animal>> animalsPositions = new HashMap<>();
    private HashMap<Vector2D,Plant> plants = new HashMap<>();
    private Random random = new Random();
    private static final int ANIMALS_NO = 100, PLANTS_NO=500;
    private int dayNumber=1;

    public WorldMap (int width, int height){
        super(width,height);
        for (int i=0;i<ANIMALS_NO;i++){
            Animal animal= new Animal(getRandomPosition(),INITIAL_ENERGY);
            animals.add(animal);
            List<Animal> animalsAtPosition = animalsPositions.get(animal.getPosition());
            placeAnimalOnMap(animal);
        }
        for (int i=0; i<PLANTS_NO;i++){
            placePlantOnMap();
        }

    }

    private void placePlantOnMap (){
        Vector2D position = getRandomPosition();
        while (isOccupiedByPlant(position)) position = getRandomPosition();
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
            animal.move(MapDirection.values()[random.nextInt(MapDirection.values().length)]);
            placeAnimalOnMap(animal);
        });
    }

    @Override
    public void reproduce(){
        List<Animal> children = new LinkedList<>();
        animalsPositions.forEach((position, animals) -> {
                List <Animal> parents = animals.stream()
                        .filter(animal -> animal.getEnergy()>INITIAL_ENERGY/2)
                        .sorted(Collections.reverseOrder())
                        .limit(2)
                        .collect(Collectors.toList());
                if (parents.size()==2){
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
                .map(animal -> animal.setEnergy(animal.getEnergy()-ENERGY_LOST))
                .filter (animal -> animal.getEnergy()>0)
                .collect(Collectors.toList());
        //mapa z miejscem i tak jest na poczatku dnia sie clearuje
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
        animal.setEnergy(animal.getEnergy()+PLANT_ENERGY);
        plants.remove(animal.getPosition());
        placePlantOnMap();
        System.out.println("Animal ate plant on position" + animal.getPosition());
    }

    public void eat(){
        animalsPositions.forEach((position, animals) -> {
            if (isOccupiedByPlant(position)){
                animals.stream()
                        .max(Animal::compareTo)
                        .ifPresent(animal -> eatPlant(animal));
            }
        });
    }
}

