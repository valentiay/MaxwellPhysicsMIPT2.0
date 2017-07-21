package experiment;

import experiment.physics.entities.Entity;

import java.util.List;

public interface Experiment {
    /**
     * Starts experiment.
     */
    void run(Settings settings);

    /**
     * Stops experiment.
     */
    void stop();

    /**
     * Getter for entities list.
     *
     * @return list of entities used in experiment
     */
    List<Entity> getEntities();
}
