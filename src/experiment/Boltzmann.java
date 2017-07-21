package experiment;

import experiment.graphics.frames.ArenaFrame;
import experiment.physics.entities.Atom;
import experiment.physics.entities.Entity;
import experiment.physics.processors.AtomProcessorBoltzmann;
import experiment.physics.Physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Boltzmann {

    // Size of the arena in pixels
    public static final int HEIGHT = 700;
    public static final int WIDTH  = 700;
    // Diameter of particles in pixels
    public static final int D = 2;
    // ArenaFrame is updated every "gasTPF" milliseconds
    public static final int gasTPF = 20;

    // Stares the increase of every particle velocity along Y axis per one frame
    public static final double boltzmannAcceleration = 100;

    // Number of holes in vertical line if ExpType - KNUDSEN
    public static int knudsenNumberOfHoles = 1;

    public static int pistonWeight = 1000;

    // Shows whether experiment is running
    private boolean active = false;


    /**
     * Runs specified experiment
     *
     * @param velocity stores start velocity of every particle along
     *                 axes ( X and Y, both velocities are equal )
     * @param numberOfAtoms stores total number of particles
     */
    public void start(int velocity, int numberOfAtoms) {
        active = true;

        final List<Entity> atoms = new ArrayList<>();
        final ArenaFrame arena;
        final Physics physics;

        arena = new ArenaFrame(atoms);
        generateAtomsFullArena(atoms, velocity, numberOfAtoms);
        physics = new Physics(atoms, new AtomProcessorBoltzmann());

        arena.setVisible(true);

        double sinceGasUpdate = 0;
        double gasTimer = System.currentTimeMillis();

        // Main loop
        while (active) {
            sinceGasUpdate += System.currentTimeMillis() - gasTimer;
            gasTimer  = System.currentTimeMillis();

            boolean gasWasUpdated = false;
            while (sinceGasUpdate > gasTPF) {
                physics.update();
                sinceGasUpdate -= gasTPF;
                gasWasUpdated = true;
            }

            if (gasWasUpdated) {
                arena.pack();
                arena.repaint();
            }
        }
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
     * Generates atoms having root mean square speed = <code>velocity</code>.
     * Velocity vector is pointed on right-down corner
     *
     * @param atoms reference to particles list
     * @param velocity start root mean square speed
     * @param numberOfAtoms total number of particles
     */
    private void generateAtomsFullArena(List<Entity> atoms, int velocity, int numberOfAtoms) {
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
    private void generateAtomsKnudsen(List<Entity> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        double v = Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 1; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH / 2);
            int y = random.nextInt(HEIGHT);
            Atom atom = new Atom(x, y, -v, v);
            atoms.add(atom);
        }
    }


    /**
     * Generates particles distributed at the arena randomly
     *
     * Generates atoms having root mean square speed as module of velocity.
     * Velocity vector is pointed randomly
     *
     * @param atoms reference to particles list
     * @param velocity start root mean square speed
     * @param numberOfAtoms total number of particles
     */
    private void generateAtomsPiston(List<Entity> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT / 2) + HEIGHT / 2;
            double cos = 2 * Math.random() - 1;
            double sin = (random.nextInt() % 2 == 0)?(-1):(1)*Math.sqrt(1 - cos * cos);
            Atom atom = new Atom(x, y, cos * velocity, sin * velocity);
            atoms.add(atom);
        }
    }
}