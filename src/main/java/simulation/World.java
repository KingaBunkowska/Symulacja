package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private static final int DAYS_NO=15;
    //private static final int WIDTH=20, HEIGHT=30;
    //private static int NUMBER_OF_DAY=1;
    private static Random random = new Random();
    //private static Animal animal= new Animal (new Vector2D(random.nextInt(WIDTH), random.nextInt(HEIGHT)),20);

    public static void main(String[] args) {
        System.out.println("Start");
        for (int i=0;i<DAYS_NO;i++) {
            Simulation.simulateDay();
        }
        System.out.println("Stop");
    }

    /*private static void run (ArrayList animals) {
        for (Animal animal:animals)
            animal.move(MapDirection.values()[random.nextInt(8)]);
    }*/

    /*private static void run () {
        for (int i=0; i<10; i++)
            animal.move(MapDirection.values()[random.nextInt(8)]);
    }*/
}
