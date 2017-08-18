package experiment.graphics.panes;


import experiment.physics.entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.List;



public class ArenaPane extends JPanel {

    private final int HEIGHT;
    private final int WIDTH ;

    private final List<Entity> entities;

    public ArenaPane(List<Entity> entities, int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.entities = entities;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);

        for (Entity entity : entities) {
            entity.render(g);
        }
    }
}