package experiment.experimentExamples;

import experiment.physics.entities.walls.LineWall;

public class WallExperiment extends CollidingAtomsExperiment {
    @Override
    protected void buildScene() {
        super.buildScene();
        entities.add(new LineWall(200, 0, 200, 200));
    }

    @Override
    protected void update(double dt) {
        super.update(dt);
    }
}
