package experiment.settings;

public class GravitySettings extends BaseSettings {

    /** Gravity acceleration */
    private double g = 5.0 / 1000;

    /** Getter for <code>g</code> */
    public double getG() {
        return g;
    }

    /** Setter for <code>g</code> */
    public void setG(double g) {
        this.g = g;
    }
}
