package experiment.experimentExamples;

import experiment.exceptions.SettingsException;
import experiment.physics.entities.atoms.AccelerationAtom;
import experiment.physics.entities.atoms.Atom;
import experiment.settings.GravitySettings;
import experiment.settings.Settings;

import java.util.LinkedList;
import java.util.Random;

public class GravityExperiment extends CollidingAtomsExperiment {

    /**
     * Fills the scene with atoms (instances of {@link AccelerationAtom})
     * having random velocity and spread randomly. Initializes atom grid.
     * Grid cell size is chosen so that number of cells was equal to
     * number of atoms.
     */
    @Override
    protected void buildScene() {
        GravitySettings gravitySettings = castToGravitySettings(this.settings);
        baseSettings = castToBaseSettings(this.settings);
        atoms = new LinkedList<>();

        initializeGrid();

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < gravitySettings.getNumberOfAtoms(); ++i) {
            int x = random.nextInt(gravitySettings.getWidth());
            int y = random.nextInt(gravitySettings.getHeight());
            double cos = 2 * Math.random() - 1;
            double sin = (random.nextInt() % 2 == 0)?(-1):(1)*Math.sqrt(1 - cos * cos);

            Atom atom = new AccelerationAtom(
                    x, y,
                    cos * gravitySettings.getVelocity(),
                    sin * gravitySettings.getVelocity(),
                    0,
                    gravitySettings.getG(),
                    gravitySettings.getRadius()
            );

            entities.add(atom);
            atoms.add(atom);
        }
    }

    /**
     * Downcasts Settings class to {@link GravitySettings} class. If cast is
     * unacceptable throws SettingsException
     *
     * @param settings settings passed to {@link experiment.Experiment#run(Settings)} method
     * @return downcasted to BaseSettings object
     * @throws SettingsException if cast is impossible
     */
    protected static GravitySettings castToGravitySettings(Settings settings) {
        try {
            return (GravitySettings) settings;
        } catch (ClassCastException e) {
            throw new SettingsException("Settings is not instance of GravitySettings");
        }
    }
}
