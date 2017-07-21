package experiment.physics.processors;

import experiment.physics.entities.Atom;
import experiment.physics.entities.Entity;

import java.util.List;

import static experiment.Boltzmann.*;

public class AtomProcessorPiston implements AtomProcessor {
    private double y = HEIGHT / 2;
    private double vy = 0;

    @Override
    public void processAtoms(List<Entity> atoms) {
        double dv = 0;
        for (Entity e : atoms) {
            Atom atom = (Atom)e;
            if (atom.y <= y) {
                atom.y = y + D;
                if (atom.vy <= 0 || atom.vy > 0 && vy > atom.vy) {
                    dv += 2 * atom.vy - 2 * vy;
                    atom.vy = -atom.vy + 2 * vy;
                }
            }
        }
        dv /= pistonWeight;
        y += vy * gasTPF / 1000;
        vy += 1000.0 * gasTPF / 1000 + dv;
        if (y >= HEIGHT && vy > 0 || y < 0 && vy < 0)
            vy = -vy;
    }

    public double getPistonY() {
        return y;
    }
}