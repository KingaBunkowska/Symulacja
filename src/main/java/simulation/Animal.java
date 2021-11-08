package simulation;

public class Animal {
    private Vector2D position;

    public Animal(Vector2D position) {
        this.position = position;
    }
    public Vector2D getPosition() {
        return position;
    }

    public void move(MapDirection direction){
        position=pbc(position.add(direction.getUnitVector()));
        System.out.println("Animal moved " + direction +"; new position is "+ position);
    }

    private Vector2D pbc(Vector2D position, int width, int height){
        int width=Simulation.getMap().getWidth();
        int height=Simulation.getMap().getHeight();

        if (position.getX()>=width) return position.substract(new Vector2D(width,0));
        if (position.getX()<0) return position.add(new Vector2D(width,0));
        if (position.getY()>=width) return position.substract(new Vector2D(0,height));
        if (position.getY()<0) return position.add(new Vector2D(0,height));

        return position;
    }
}
