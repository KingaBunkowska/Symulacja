package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private static Random random = new Random();
    private static Animal animal= new Animal (new Vector2D(random.nextInt(100), random.nextInt(100)));

    public static void main(String[] args) {
        System.out.println("Start");

        System.out.println("Stop");
    }

    private static void run () {
        for (int i=0; i<10; i++)
            animal.move(MapDirection.values()[random.nextInt(8)]);
    }

    /*private static void runFromDirections (List<MapDirection> directions){
        for (MapDirection direction : directions){
            switch (direction) {
                case NORTH: System.out.println("Animal moves north"); break;
                case NORTH_EAST: System.out.println("Animal moves north-east"); break;
                case EAST: System.out.println("Animal moves east"); break;
                case SOUTH_EAST: System.out.println("Animal moves south-east"); break;
                case SOUTH: System.out.println("Animal moves south"); break;
                case SOUTH_WEST: System.out.println("Animal moves south-west"); break;
                case WEST: System.out.println("Animal moves west"); break;
                case NORTH_WEST: System.out.println("Animal moves north-west"); break;
            }
        }
    }

    private static List<MapDirection> parseToEnum (String[] args) {
        List<MapDirection> directions = new ArrayList<>();
        for (String arg : args){
            switch (arg) {
                case "n": directions.add(MapDirection.NORTH); break;
                case "ne": directions.add(MapDirection.NORTH_EAST); break;
                case "e": directions.add(MapDirection.EAST); break;
                case "se": directions.add(MapDirection.SOUTH_EAST); break;
                case "s": directions.add(MapDirection.SOUTH); break;
                case "sw": directions.add(MapDirection.SOUTH_WEST); break;
                case "w": directions.add(MapDirection.WEST); break;
                case "nw": directions.add(MapDirection.NORTH_WEST); break;
            }
        }
        return directions;
    }*/

}
