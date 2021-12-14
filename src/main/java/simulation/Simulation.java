package simulation;

public class Simulation {
    private static final WorldMap map = new WorldMap();

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

    public static void setSimulation() {
        map.setSimulation();
    }

}
