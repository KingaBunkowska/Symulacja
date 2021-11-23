package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private static final int DAYS_NO=15;

    public static void main(String[] args) {
        System.out.println("Start");
        for (int i=0;i<DAYS_NO;i++) {
            Simulation.simulateDay();
        }
        System.out.println("Stop");
    }
}
