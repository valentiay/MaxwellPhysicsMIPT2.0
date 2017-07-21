package experiment;

import experiment.physics.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExperiment implements Experiment {

    private List<Entity> entities;

    private boolean isRunning = false;

    @Override
    public void run(Settings settings) {
        Thread experimentThread = new Thread(() -> {
            isRunning = true;

            entities = new ArrayList<>();
//            final ArenaFrame arena;
//            final Physics physics;
//            arena = new ArenaFrame(atoms);
//            physics = new Physics(atoms, new AtomProcessorBoltzmann());
//
//
            double sinceGasUpdate = 0;
            double gasTimer = System.currentTimeMillis();

            // Main loop
            while (isRunning) {
                sinceGasUpdate += System.currentTimeMillis() - gasTimer;
                gasTimer  = System.currentTimeMillis();

                boolean gasWasUpdated = false;
                while (sinceGasUpdate > settings.dt) {
                    update(settings.dt);
                    sinceGasUpdate -= settings.dt;
                    gasWasUpdated = true;
                }

                if (gasWasUpdated) {
                    render();
                }
            }
//            arena.dispose();

        });
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    @Override
    public List<Entity> getEntities() {
        return entities;
    }

    abstract protected void buildScene();

    protected void update(double dt) {
        for (Entity entity : entities) {
            entity.update(dt);
        }
    }

    protected void render() {

    }
}
