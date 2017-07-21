package experiment.physics.processors;

import experiment.physics.entities.Entity;

import java.util.List;



/**
 * Instances of this interface are invoked every frame to process all particles
 */
public interface AtomProcessor {
    void processAtoms(List<Entity> atoms);
}
