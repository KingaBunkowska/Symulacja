package simulation;

import java.util.List;
import java.util.Map;

public interface IWorldMap {
    int getWidth();
    int getHeight();
    void run();
    void eat();
    void atTheEndOfDay();
    void reproduce();
    void fight();
    Map<Vector2D, Plant> getPlantsLocations();
    Map<Vector2D, List<Animal>> getAnimalLocations();
}
