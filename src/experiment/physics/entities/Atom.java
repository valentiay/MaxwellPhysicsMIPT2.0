package experiment.physics.entities;


import java.awt.*;

/**
 * Represents the instance of the particle of circle shape
 */
public class Atom implements Entity{

    public Atom(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public double x;
    public double y;
    public double vx;
    public double vy;

    @Override
    public void update(double dt) {

    }

    @Override
    public void render(Graphics2D g) {

    }
}