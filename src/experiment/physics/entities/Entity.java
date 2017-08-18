package experiment.physics.entities;

import java.awt.*;

public interface Entity {
    /**
     * Updates entity condition.
     *
     * @param dt time interval since last update
     */
    void update(double dt);

    /**
     * Draws entity on swing graphics context.
     *
     * @param g graphics context
     */
    void render(Graphics g);
}
