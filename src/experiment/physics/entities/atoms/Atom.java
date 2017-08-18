package experiment.physics.entities.atoms;


import experiment.physics.entities.Entity;

import java.awt.*;

/**
 * Represents the instance of the particle of circle shape
 */
public class Atom implements Entity {

    /** x coordinate of particle center. */
    public double x;

    /** y coordinate of particle center. */
    public double y;

    /** Horizontal component of velocity. */
    public double vx;

    /** Vertical component of velocity. */
    public double vy;

    /** Particle radius. */
    private int r;

    /**
     * Constructs atom with passed parameters.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @param vx x velocity.
     * @param vy y velocity.
     * @param r Particle radius.
     */
    public Atom(double x, double y, double vx, double vy, int r) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.r = r;
    }

    /**
     * Moves particle according to its velocity.
     *
     * @param dt time interval since last experiment update.
     */
    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    /**
     * Draws circle to window context.
     *
     * @param g graphics context.
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int)x - r, (int)y - r, 2 * r, 2 * r);
    }
}