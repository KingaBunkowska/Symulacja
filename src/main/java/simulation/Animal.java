package simulation;

import java.util.Random;

public class Animal implements Comparable<Animal>{
    private Vector2D position;
    private static int counter=0;
    private final int animalId;
    private int energy;
    private int age=1;
    private final Genome genome;
    private int numberOfChildren=0;
    private final boolean gender; // 0-female, 1-male

    public Animal(Vector2D position, int energy) {
        this.position = position;
        this.energy=energy;
        animalId=counter++;
        this.genome = new Genome();
        gender = new Random().nextBoolean();
    }

    public Animal(Animal mother, Animal father) {
        Vector2D direction = MapDirection.values()[new Random().nextInt(MapDirection.values().length)].getUnitVector();
        this.position = pbc(mother.getPosition().add(direction));
        this.energy=(mother.getEnergy()+father.getEnergy())/4;
            mother.setEnergy(mother.getEnergy()*3/4);
            father.setEnergy(father.getEnergy()*3/4);
        mother.increaseNoOfChildren();
        father.increaseNoOfChildren();
        animalId=counter++;
        this.genome = new Genome(mother.getGenome(), father.getGenome());
        gender = new Random().nextBoolean();
    }

    public boolean isMale(){
        return gender;
    }

    public Genome getGenome() {
        return genome;
    }

    public Vector2D getPosition() {
        return position;
    }

    public int compareTo(Animal animal){
        return getEnergy()==animal.getEnergy() ? getAnimalId()-animal.getAnimalId(): getEnergy()-animal.getEnergy();
    }

    public int getEnergy(){
        return energy;
    }

    public int getAge() {
        return age;
    }

    public Animal setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public int getAnimalId() {
        return animalId;
    }

    public Animal aging (){
        age++;
        return this;
    }

    public void move(MapDirection direction){
        position=pbc(position.add(direction.getUnitVector()));
        //System.out.println("Animal" +" "+animalId+" "+"moved " + direction +"; new position is "+ position + ": energy: "+energy+ ": age: "+age);
    }

    public void moveBasedOnGenome(){
        move(genome.getRandomMove());
    }

    private Vector2D pbc(Vector2D position){
        int width=Simulation.getMap().getWidth();
        int height=Simulation.getMap().getHeight();

        if (position.x() < 0) return position.add(new Vector2D(width, 0));
        if (position.x() >= width) return position.substract(new Vector2D(width, 0));
        if (position.y() < 0) return position.add(new Vector2D(0, height));
        if (position.y() >= height) return position.substract(new Vector2D(0, height));

        return position;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void increaseNoOfChildren(){
        numberOfChildren++;
    }
}
