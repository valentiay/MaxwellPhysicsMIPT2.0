package Maxwell.Experiment;

import Maxwell.ExpType;
import Maxwell.Experiment.frames.Arena;
import Maxwell.Experiment.frames.WallPainterKnudsen;
import Maxwell.Experiment.physics.Atom;
import Maxwell.Experiment.physics.AtomProcessorBoltzmann;
import Maxwell.Experiment.physics.AtomProcessorKnudsen;
import Maxwell.Experiment.physics.Physics;
import Maxwell.Experiment.plot.Plot;
import Maxwell.Experiment.plot.PlotDistribution.PlotBoltzmann;
import Maxwell.Experiment.plot.PlotDistribution.PlotMaxwell;
import Maxwell.Experiment.plot.PlotTimeFunction.PlotKnudsen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Experiment {

    // Size of the arena in pixels
    public static final int HEIGHT = 700;
    public static final int WIDTH  = 700;
    // Diameter of particles in pixels
    public static final int D = 2;
    // Arena is updated every "gasTPF" milliseconds
    public static final int gasTPF = 20;
    // Plot is updated every "plotTPF" milliseconds
    public static final int plotTPF = 1000;

    // Stares the increase of every particle velocity along Y axis per one frame
    public static final double boltzmannAcceleration = 100;

    // Number of holes in vertical line if ExpType - KNUDSEN
    public static final int knudsenNumberOfHoles = 10;

    // Shows whether the arena should be updated
    private boolean active = false;

    /**
     * Runs specified experiment
     *
     * @param expType determines which experiment is intended to run
     * @param velocity stores start velocity of every particle along
     *                 axes ( X and Y, both velocities are equal )
     * @param numberOfAtoms stores total number of particles
     */
    public void start(ExpType expType, int velocity, int numberOfAtoms) {
        active = true;

        final List<Atom> atoms = new ArrayList<>();
        final Arena arena;
        final Physics physics;
        final Plot plot;

        // Running different experiments according to the specified value
        switch (expType) {
            case MAXWELL:
                arena = new Arena(atoms);
                generateAtomsFullArena(atoms, velocity, numberOfAtoms);
                plot = new PlotMaxwell(atoms);
                physics = new Physics(atoms);
                break;
            case BOLTZMANN:
                arena = new Arena(atoms);
                generateAtomsFullArena(atoms, velocity, numberOfAtoms);
                plot = new PlotBoltzmann(atoms);
                // see @AtomProcessorBoltzmann
                physics = new Physics(atoms, new AtomProcessorBoltzmann());
                break;
            case KNUDSEN:
                arena = new Arena(atoms, new WallPainterKnudsen());
                generateAtomsKnudsen(atoms, velocity, numberOfAtoms);
                plot = new PlotKnudsen(atoms);
                // see @AtomProcessorKnudsen
                physics = new Physics(atoms, new AtomProcessorKnudsen(numberOfAtoms));
                break;
            default:
                arena = null;
                plot = null;
                physics = null;
                break;
        }

        arena.setVisible(true);

        double sinceGasUpdate = 0;
        double sincePlotUpdate = 0;
        double gasTimer = System.currentTimeMillis();
        double plotTimer = System.currentTimeMillis();

        // Main loop
        while (active) {
            sinceGasUpdate += System.currentTimeMillis() - gasTimer;
            sincePlotUpdate += System.currentTimeMillis() - plotTimer;
            gasTimer  = System.currentTimeMillis();
            plotTimer = System.currentTimeMillis();

            while (sinceGasUpdate > gasTPF) {
                physics.update();
                sinceGasUpdate -= gasTPF;
            }

            if (sincePlotUpdate > plotTPF) {
                plot.render();
                sincePlotUpdate = 0;
            }

            arena.pack();
            arena.repaint();
        }

        // Updating image on the screen
        plot.dispose();
        arena.dispose();
    }


    /**
     * It does what you think it does ( © C++ STL ;) )
     */
    public void stop() {
        active = false;
    }


    /**
     * Generates particles distributed at the arena randomly
     *
     * @param atoms reference to particles list
     * @param velocity start velocity along X and Y axes for every particle
     * @param numberOfAtoms total number of particles
     */
    private void generateAtomsFullArena(List<Atom> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        double v = Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            Atom atom = new Atom(x, y, v, v);
            atoms.add(atom);
        }
    }


    /**
     * Generates particles distributed at left half (!) of the arena randomly
     *
     * @param atoms reference to particles list
     * @param velocity start velocity along X and Y axes for every particle
     * @param numberOfAtoms total number of particles
     */
    private void generateAtomsKnudsen(List<Atom> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        double v = Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH / 2);
            int y = random.nextInt(HEIGHT);
            Atom atom = new Atom(x, y, -v, v);
            atoms.add(atom);
        }
    }
}