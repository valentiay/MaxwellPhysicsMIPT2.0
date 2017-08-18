package experiment;

import experiment.graphics.frames.SceneFrame;
import experiment.physics.entities.Entity;
import experiment.settings.Settings;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExperiment implements Experiment {

    /** List of entities on the scene. */
    protected List<Entity> entities;

    /** Experiment parameters. */
    protected Settings settings;

    /**
     * Flag which is true if experiment is running and false otherwise.
     * Used for stopping experiment thread.
     */
    private boolean isRunning = false;

    /**
     * Object representing window where the scene is drawn.
     */
    private SceneFrame sceneFrame;

    /**
     * Configures experiment and starts experiment loop.
     *
     * @param settings experiment parameters.
     *
     * @see Settings
     * @see AbstractExperiment#buildScene()
     */
    @Override
    public final void run(Settings settings) {
        this.settings = settings;
        Thread thread = new Thread(() -> {
            isRunning = true;

            entities = new ArrayList<>();

            sceneFrame = new SceneFrame(entities, settings.getWidth(), settings.getHeight());
            sceneFrame.setVisible(true);

            buildScene();

            double sinceGasUpdate = 0;
            double gasTimer = System.currentTimeMillis();

            // Main loop
            while (isRunning) {
                sinceGasUpdate += System.currentTimeMillis() - gasTimer;
                gasTimer  = System.currentTimeMillis();

                boolean sceneWasUpdated = false;
                while (sinceGasUpdate > settings.getDt()) {
                    update(settings.getDt());
                    sinceGasUpdate -= settings.getDt();
                    sceneWasUpdated = true;
                }

                if (sceneWasUpdated) {
                    render();
                }
            }

            sceneFrame.dispose();

            this.settings = null;
            entities = null;
            sceneFrame = null;
        });
        thread.run();
    }

    @Override
    public final void stop() {
        isRunning = false;
    }

    @Override
    public final List<Entity> getEntities() {
        return entities;
    }

    /**
     * Fills the scene with entities. This method is called once before the experiment loop starts.
     */
    abstract protected void buildScene();

    /**
     * Updates the scene every <code>dt</code> milliseconds. By default
     * calls {@link Entity#update(double)} method of every entity on the scene.
     *
     * @param dt time in milliseconds between two scene updates.
     * @see Entity#update(double)
     */
    protected void update(double dt) {
        for (Entity entity : entities) {
            entity.update(dt);
        }
    }

    /**
     * Redraws scene window
     */
    private void render() {
        sceneFrame.pack();
        sceneFrame.repaint();
    }
}
