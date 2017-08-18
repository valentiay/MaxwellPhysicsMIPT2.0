package experiment.settings;

/**
 * Settings for base experiment.
 */
public class BaseSettings implements Settings {
    /** Time between two scene updates */
    private double dt = 1000.0 / 60;

    /** Scene width */
    private int width = 500;

    /** Scene height */
    private int height = 500;

    /** Number of atoms on the scene */
    private int numberOfAtoms = 1000;

    /** Atom radius */
    private int radius = 3;

    /** Mean square speed of atoms */
    private double velocity = 100.0 / 1000;

    @Override
    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumberOfAtoms() {
        return numberOfAtoms;
    }

    public void setNumberOfAtoms(int numberOfAtoms) {
        this.numberOfAtoms = numberOfAtoms;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
