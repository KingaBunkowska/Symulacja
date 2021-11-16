package simulation;

public class Animal {
    private Vector2D position;
    private int energy;
    private int age;

    public Animal(Vector2D position, int energy) {
        this.position = position;
        this.energy=energy;
        this.age=1;
    }
    public Vector2D getPosition() {
        return position;
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

    public Animal aging (){
        age++;
        return this;
    }

    public void move(MapDirection direction){
        position=pbc(position.add(direction.getUnitVector()));
        System.out.println("Animal moved " + direction +"; new position is "+ position);
    }



    private Vector2D pbc(Vector2D position){
        int width=Simulation.getMap().getWidth();
        int height=Simulation.getMap().getHeight();

        if (position.getX()>=width) return position.substract(new Vector2D(width,0));
        if (position.getX()<0) return position.add(new Vector2D(width,0));
        if (position.getY()>=width) return position.substract(new Vector2D(0,height));
        if (position.getY()<0) return position.add(new Vector2D(0,height));

        return position;
    }
}
