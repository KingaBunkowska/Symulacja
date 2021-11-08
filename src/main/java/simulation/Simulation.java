package simulation;

public class Simulation {
    private static final int WIDTH=20, HEIGHT=30;
    private static final WorldMap map = new WorldMap(WIDTH, HEIGHT);

    public static WorldMap getMap(){
        return map;
    }
    public static void simulateDay(){
        map.run();
    }

}
