package simulation;

public class Simulation {
    private static final WorldMap map = new WorldMap(
            SimulationParams.getField("width"),
            SimulationParams.getField("height"),
            SimulationParams.getField("noOfAnimals"),
            SimulationParams.getField("noOfPlants"),
            SimulationParams.getField("animalEnergy"),
            SimulationParams.getField("plantEnergy")
    );

    public static WorldMap getMap(){
        return map;
    }
    public static void simulateDay(){
        map.run();
        map.eat();
        map.fight();
        map.reproduce();
        map.atTheEndOfDay();
    }

}
