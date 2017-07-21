package experiment.graphics.panes;


import experiment.Boltzmann;
import experiment.graphics.painters.AtomsPainter;
import experiment.physics.entities.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.List;



public class ArenaPane extends JPanel {

    public final int HEIGHT;
    public final int WIDTH ;

    private final List<Entity> atoms;

    private final experiment.graphics.painters.Painter atomsPainter;
    private final experiment.graphics.painters.Painter wallPainter;

    public ArenaPane(List<Entity> atoms) {
        this.HEIGHT = Boltzmann.HEIGHT;
        this.WIDTH = Boltzmann.WIDTH;
        this.atoms = atoms;
        this.atomsPainter = new AtomsPainter();
        this.wallPainter = null;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.BLACK);

        atomsPainter.paint(g, atoms);

        if (wallPainter != null) {
            wallPainter.paint(g);
        }
    }
}