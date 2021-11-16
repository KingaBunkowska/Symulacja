package simulation;

import java.util.*;

public class WorldMap extends AbstractWorldMap{

    private ArrayList<Animal> animals = new ArrayList<>();
    private static final int INITIAL_ENERGY=20;
    private HashMap<Vector2D, LinkedList<Animal>> animalsPositions = new HashMap<>();
    private HashMap<Vector2D,Plant> plants = new HashMap<>();
    private Random random = new Random();
    private static final int ANIMALS_NO = 10, PLANTS_NO=100;

    public WorldMap (int width, int height){
        super(width,height);
        for (int i=0;i<ANIMALS_NO;i++){
            Animal animal= new Animal(getRandomPosition(),INITIAL_ENERGY);
            animals.add(animal);
            List<Animal> animalsAtPosition = animalsPositions.get(animal.getPosition());
            placeAnimalOnMap(animal);
            //animalsPositions.put(animal.getPosition(), animal);
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

    /*private void placeAnimalOnMap(Animal animal){
        List<Animal> animalsAtPosition = animalsPositions.get(animal.getPosition());
        if (animalsAtPosition == null){
            animalsAtPosition = new LinkedList<>();
            animalsPositions.put(animal.getPosition(), (LinkedList<Animal>) animalsAtPosition);
        }
        animalsAtPosition.add(animal);
    }*/
    @Override
    /*public void  run (){
        for (Animal animal:animals){
            animal.move(MapDirection.values()[random.nextInt(MapDirection.values().length)]);
        }
    }*/
    public void run (){
        animalsPositions.clear();
        for (Animal animal:animals){
            animal.move(MapDirection.values()[random.nextInt(MapDirection.values().length)]);
            placeAnimalOnMap(animal);
        }
    }

    public void eat(){
        for (Animal animal : animals){
            if (isOccupiedByPlant(animal.getPosition())){
                plants.remove(animal.getPosition());
                placePlantOnMap();
                System.out.println("Animal ate plant on position" + animal.getPosition());
            }

        }
    }

}
