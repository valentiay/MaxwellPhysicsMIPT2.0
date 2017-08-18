package experiment.settings;

public interface Settings {

    /**
     * Getter for dt.
     *
     * @return time between two scene updates in milliseconds.
     */
    double getDt();

    /**
     * Getter for width.
     *
     * @return scene width in px.
     */
    int getWidth();

    /**
     * Getter for height.
     *
     * @return scene height in px.
     */
    int getHeight();
}
