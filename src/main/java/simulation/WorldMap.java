package simulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class WorldMap extends AbstractWorldMap{

    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private LinkedList<Plant> plants = new LinkedList<Plant>();
    private Random random = new Random();
    private static final int ANIMALS_NO = 10, PLANTS_NO=100;

    public WorldMap (int width, int height){
        super(width,height);
        for (int i=0;i<ANIMALS_NO;i++){
            animals.add(new Animal(getRandomPosition()));
        }
        for (int i=0; i<PLANTS_NO;i++){
            placePlantOnMap();
        }

    }

    private void placePlantOnMap (){
        Vector2D position = getRandomPosition();
        plants.add(new Plant(getRandomPosition()));
        while (isOccupiedByPlant(position)) position = getRandomPosition();
        plants.add(new Plant (position));
    }

    private boolean isOccupiedByPlant(Vector2D position){
        return getPlantAtPosition(position) != null;
    }

    private Plant getPlantAtPosition(Vector2D position){
        for (Plant plant:plants){
            if (plant.getPosition().equals(position)) return plant;
        }
        return null;
    }

    private Vector2D getRandomPosition (){
        return new Vector2D(random.nextInt(getWidth()), random.nextInt(getHeight()));
    }

    @Override
    public void  run (){
        for (Animal animal:animals){
            animal.move(MapDirection.values()[random.nextInt(MapDirection.values().length)]);
        }
    }
    public void eat(){
        for (Animal animal:animals){
            Plant plant = getPlantAtPosition(animal.getPosition());
            if (plant != null) {
                plants.remove(plant);
                placePlantOnMap();
                System.out.println("Animal ate plant");
            }
        }
    }

}
