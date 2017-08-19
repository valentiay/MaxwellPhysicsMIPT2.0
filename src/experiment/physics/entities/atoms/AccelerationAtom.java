package experiment.physics.entities.atoms;

public class AccelerationAtom extends Atom {
    /** Acceleration along x axis */
    private double ax;

    /** Acceleration along y axis */
    private double ay;

    /**
     * Constructs atom without acceleration.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @param vx x axis velocity.
     * @param vy y axis velocity.
     * @param r radius.
     */
    public AccelerationAtom(double x, double y, double vx, double vy, int r) {
        super(x, y, vx, vy, r);
        ax = 0;
        ay = 0;
    }

    /**
     * Constructs atom having acceleration.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @param vx x axis velocity.
     * @param vy y axis velocity.
     * @param ax x axis acceleration.
     * @param ay y axis acceleration.
     * @param r radius.
     */
    public AccelerationAtom(double x, double y, double vx, double vy, double ax, double ay, int r) {
        super(x, y, vx, vy, r);
        this.ax = ax;
        this.ay = ay;
    }

    /**
     * Updates atom coordinates and increases velocity by required acceleration.
     *
     * @param dt time interval since last experiment update.
     */
    @Override
    public void update(double dt) {
        super.update(dt);
        vx += ax;
        vy += ay;
    }

    /** X axis acceleration getter */
    public double getAx() {
        return ax;
    }

    /** X axis acceleration setter */
    public void setAx(double ax) {
        this.ax = ax;
    }

    /** Y axis acceleration getter */
    public double getAy() {
        return ay;
    }

    /** Y axis acceleration setter */
    public void setAy(double ay) {
        this.ay = ay;
    }
}
