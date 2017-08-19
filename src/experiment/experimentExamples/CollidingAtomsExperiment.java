package experiment.experimentExamples;

import experiment.AbstractExperiment;
import experiment.exceptions.SettingsException;
import experiment.physics.Grid;
import experiment.physics.entities.atoms.Atom;
import experiment.settings.BaseSettings;
import experiment.settings.Settings;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CollidingAtomsExperiment extends AbstractExperiment {
    /**
     * List of atoms - small circle particles.
     *
     * @see Atom
     */
    protected List<Atom> atoms;

    /**
     * Extended settings for experiment with colliding atoms.
     * Initialized when {@link CollidingAtomsExperiment#buildScene()} method is called.
     *
     * @see BaseSettings
     * @see CollidingAtomsExperiment#buildScene()
     */
    protected BaseSettings baseSettings;

    /**
     * Scene grid. Used to optimise atom collision procession.
     *
     * @see Grid
     */
    protected Grid grid;

    /**
     * Fills the scene with atoms having random velocity and spread randomly.
     * Initializes atom grid. Grid cell size is chosen so that number of cells was equal to
     * number of atoms.
     */
    @Override
    protected void buildScene() {
        baseSettings = castToBaseSettings(this.settings);
        atoms = new LinkedList<>();

        int cellSize = Math.max(
                (int)Math.sqrt(baseSettings.getWidth() * baseSettings.getHeight() / baseSettings.getNumberOfAtoms()),
                8 * baseSettings.getRadius()
        );

        grid = new Grid(baseSettings.getWidth(), baseSettings.getHeight(), cellSize);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < baseSettings.getNumberOfAtoms(); ++i) {
            int x = random.nextInt(baseSettings.getWidth());
            int y = random.nextInt(baseSettings.getHeight());
            double cos = 2 * Math.random() - 1;
            double sin = (random.nextInt() % 2 == 0)?(-1):(1)*Math.sqrt(1 - cos * cos);

            Atom atom = new Atom(
                    x, y,
                    cos * baseSettings.getVelocity(),
                    sin * baseSettings.getVelocity(),
                    baseSettings.getRadius()
            );

            entities.add(atom);
            atoms.add(atom);
        }
    }

    /**
     * Processes atom to atom collisions and border collisions.
     *
     * @param dt time in milliseconds between two scene updates.
     * @see CollidingAtomsExperiment#processAtomToAtomCollisions()
     * @see CollidingAtomsExperiment#processBorderCollisions()
     */
    @Override
    protected void update(double dt) {
        super.update(dt);
        grid.build(atoms);
        processBorderCollisions();
        processAtomToAtomCollisions();
    }

    /**
     * Processes collisions with scene borders for every atom.
     * Changes necessary velocity component if atom is intersects window border.
     */
    protected void processBorderCollisions() {
        for (Atom atom : atoms) {
            if (atom.x + baseSettings.getRadius() > baseSettings.getWidth() && atom.vx > 0) {
                atom.vx = -atom.vx;
            }
            if (atom.y + baseSettings.getRadius() > baseSettings.getHeight() && atom.vy > 0) {
                atom.vy = -atom.vy;
            }
            if (atom.x < baseSettings.getRadius() && atom.vx < 0) {
                atom.vx = -atom.vx;
            }
            if (atom.y < baseSettings.getRadius() && atom.vy < 0) {
                atom.vy = -atom.vy;
            }
        }
    }

    /**
     * Processes atom to atom collisions. Uses grid to optimize the process.
     * Only atoms in neighbour cells are checked for collisions. For more
     * information about the process check out {@link Grid#getNeighbourAtoms(int, int)} method
     *
     * @see Grid
     * @see CollidingAtomsExperiment#processAtomToAtomCollision(Atom, Atom)
     */
    protected void processAtomToAtomCollisions () {
        for (int i = 0; i < grid.getGridWidth(); i++) {
            for (int j = 0; j < grid.getGridHeight(); j++) {
                List<Atom> cell = grid.getAt(i, j);
                List<Atom> neighbors = grid.getNeighbourAtoms(i, j);
                for (Atom atom1 : cell) {
                    for (Atom atom2 : neighbors) {
                        processAtomToAtomCollision(atom1, atom2);
                    }
                }
            }
        }
    }

    /**
     * Processes collision of two atoms. Velocities are changed according to physical laws.
     * Atom swap their normal velocity components and keep their tangent components.
     * After velocity change atoms are moved so they wouldn't intersect
     *
     * @param atom1 First colliding atom
     * @param atom2 Second colliding atom
     */
    protected void processAtomToAtomCollision(Atom atom1, Atom atom2) {
        double dx = atom1.x - atom2.x;
        double dy = atom1.y - atom2.y;
        double lenSqr = dx * dx + dy * dy;

        if (lenSqr < 4 * baseSettings.getRadius() * baseSettings.getRadius()) {

            if (lenSqr == 0) {
                return;
            }

            double alpha1 = (atom1.vx * dx + atom1.vy * dy) / (lenSqr);
            double alpha2 = (atom2.vx * dx + atom2.vy * dy) / (lenSqr);
            double p1x = alpha1 * dx;
            double p1y = alpha1 * dy;
            double p2x = alpha2 * dx;
            double p2y = alpha2 * dy;

            atom1.vx = atom1.vx - p1x + p2x;
            atom1.vy = atom1.vy - p1y + p2y;
            atom2.vx = atom2.vx - p2x + p1x;
            atom2.vy = atom2.vy - p2y + p1y;

            atom1.x = atom2.x + dx * 2 * baseSettings.getRadius() / Math.sqrt(lenSqr);
            atom1.y = atom2.y + dy * 2 * baseSettings.getRadius() / Math.sqrt(lenSqr);
        }
    }

    /**
     * Down casts Settings class to {@link BaseSettings} class. If cast is
     * unacceptable throws SettingsException
     *
     * @param settings settings passed to {@link experiment.Experiment#run(Settings)} method
     * @return down casted to BaseSettings object
     * @throws SettingsException if cast is impossible
     */
    protected static BaseSettings castToBaseSettings (Settings settings) {
        try {
            return (BaseSettings) settings;
        } catch (ClassCastException e) {
            throw new SettingsException("Settings is not instance of BaseSettings");
        }
    }
}
