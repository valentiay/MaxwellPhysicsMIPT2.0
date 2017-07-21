package experiment.graphics.painters;


import experiment.physics.entities.Atom;
import experiment.physics.entities.Entity;
import experiment.Boltzmann;

import java.util.List;
import java.awt.*;

public class AtomsPainter extends Painter {

    @Override
    public void paint(Graphics g, List<Entity> atoms) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);
        for (Entity e : atoms) {
            Atom atom = (Atom)e;
            g2.fillOval((int)atom.x, (int)atom.y, Boltzmann.D, Boltzmann.D);
        }
    }
}
