package experiment.graphics.frames;
import experiment.graphics.panes.ArenaPane;
import experiment.physics.entities.Entity;

import javax.swing.*;
import java.util.List;



public class ArenaFrame extends JFrame {

    /**
     * Constructor of arena without any objects except particles on it
     * @param atoms reference to the list of particles
     */
    public ArenaFrame(List<Entity> atoms) {
        super("Газ");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new ArenaPane(atoms));
    }
}
