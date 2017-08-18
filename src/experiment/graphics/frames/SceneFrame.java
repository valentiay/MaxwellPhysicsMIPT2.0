package experiment.graphics.frames;
import experiment.graphics.panes.ArenaPane;
import experiment.physics.entities.Entity;

import javax.swing.*;
import java.util.List;



public class SceneFrame extends JFrame {

    /**
     * Constructs window where entities are drawn
     * @param entities list of entities to draw
     * @param width width of arena window
     * @param height height of arena window
     */
    public SceneFrame(List<Entity> entities, int width, int height) {
        super("Газ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new ArenaPane(entities, width, height));
    }
}
