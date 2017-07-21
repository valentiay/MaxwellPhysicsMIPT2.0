package experiment.physics.processors;

import experiment.physics.entities.Atom;
import experiment.physics.entities.Entity;

import java.util.List;

import static experiment.Boltzmann.*;



/**
 * Am implementation of @processors for Boltzmann experiment
 */
public class AtomProcessorBoltzmann implements AtomProcessor {

    @Override
    public void processAtoms(List<Entity> atoms) {
        for (Entity e : atoms) {
            Atom atom = (Atom)e;
            if (atom.y <= HEIGHT - D)
                atom.vy += boltzmannAcceleration * gasTPF / 1000;
        }
    }
}
