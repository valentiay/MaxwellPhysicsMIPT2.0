package experiment.physics.processors;

import experiment.physics.entities.Atom;
import experiment.physics.entities.Entity;

import java.util.ArrayList;
import java.util.List;

import static experiment.Boltzmann.*;



/**
 * Am implementation of @processors for IdealGas experiment
 */
public class AtomProcessorKnudsen implements AtomProcessor {
    private final List<Boolean> isLeftSide;

    public AtomProcessorKnudsen(int numberOfAtoms) {
        isLeftSide = new ArrayList<>(numberOfAtoms);
        for (int i = 0; i < numberOfAtoms; i++)
            isLeftSide.add(true);
    }



    @Override
    public void processAtoms(List<Entity> atoms) {
        for (int i = 0; i < atoms.size(); i++) {
            Atom atom = (Atom)atoms.get(i);
            if (isLeftSide.get(i)) {
                if (atom.vx > 0 && atom.x > WIDTH / 2) {
                    if (isNotInHole(atom)) {
                        atom.vx = -atom.vx;
                    } else {
                        isLeftSide.set(i, false);
                    }
                }
            } else {
                if (atom.vx < 0 && atom.x < WIDTH / 2) {
                    if (isNotInHole(atom)) {
                        atom.vx = -atom.vx;
                    } else {
                        isLeftSide.set(i, true);
                    }
                }
            }
        }
    }



    private boolean isNotInHole(Atom atom) {
        for (int i = 1; i < knudsenNumberOfHoles + 1; i++)
            if (atom.y >= i * HEIGHT / (knudsenNumberOfHoles + 1) - D / 2
                    && atom.y <= i * HEIGHT / (knudsenNumberOfHoles + 1) + D / 2)
                return false;
        return true;
    }
}