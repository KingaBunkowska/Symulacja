package simulation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Genome {
    //skłonność do poruszania się w danym kierunku
    private static final int GENOM_LENGHT=32;
    private List<MapDirection> genome = new LinkedList<>();
    private Random random = new Random();

    public Genome(){
        new Random().ints(GENOM_LENGHT,0,MapDirection.values().length)
                .forEach(i -> genome.add(MapDirection.values()[i]));
    }

    public Genome(Genome mother, Genome father){

        int split = random.nextInt(29)+1;
        IntStream.range(0,GENOM_LENGHT).forEach(i -> genome.add((i < split ? mother : father).genome.get(i)));
    }

}
