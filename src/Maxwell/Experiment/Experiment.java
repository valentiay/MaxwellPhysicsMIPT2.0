package Maxwell.Experiment;

import Maxwell.ExpType;
import Maxwell.Experiment.frames.Arena;
import Maxwell.Experiment.physics.Atom;
import Maxwell.Experiment.physics.Drawer;
import Maxwell.Experiment.physics.Physics;
import Maxwell.Experiment.plot.Plot;
import Maxwell.Experiment.plot.PlotBolzman;
import Maxwell.Experiment.plot.PlotMaxwell;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Experiment {

    public static final int HEIGHT = 700;
    public static final int WIDTH  = 700;
    public static final int D = 2;

    private static final double boltzmannAcceleration = 1;

    private boolean active = false;

    public void start(ExpType expType, int velocity, int numberOfAtoms) {
        active = true;

        int gasTPF = 20;
        int plotTPF = 1000;

        final List<Atom> atoms = new ArrayList<>();
        final Drawer drawer = new Drawer(atoms);
        final Arena arena = new Arena();
        final Physics physics;
        final Plot plot ;

        switch (expType) {
            case MAXWELL:
                plot = new PlotMaxwell(atoms, velocity);
                physics = new Physics(atoms);
                break;
            case BOLTZMANN:
                plot = new PlotBolzman(atoms, velocity, boltzmannAcceleration * gasTPF, HEIGHT);
                physics = new Physics(atoms, atoms1 -> {
                    for (Atom atom : atoms)
                        atom.vy += boltzmannAcceleration * gasTPF;
                });
                break;
            case KNUDSEN:
                final List<Boolean> isLeftSide = new ArrayList<>(numberOfAtoms);
                for (int i = 0; i < numberOfAtoms; i++)
                    isLeftSide.add(true);

                plot = new PlotMaxwell(atoms, velocity);
                physics = new Physics(atoms, atoms1 -> {
                    for (int i = 0; i < atoms.size(); i++) {
                        Atom atom = atoms.get(i);
                        if (isLeftSide.get(i)) {
                            if (atom.vx > 0 && atom.x > WIDTH / 2) {
                                if (atom.y < HEIGHT / 2 - D / 2 || atom.y > HEIGHT / 2 + D / 2) {
                                    atom.vx = -atom.vx;
                                } else {
                                    isLeftSide.set(i, false);
                                }
                            }
                        } else {
                            if (atom.vx < 0 && atom.x < WIDTH / 2) {
                                if (atom.y < HEIGHT / 2 - D / 2 || atom.y > HEIGHT / 2 + D / 2) {
                                    atom.vx = -atom.vx;
                                } else {
                                    isLeftSide.set(i, true);
                                }
                            }
                        }
                    }
                });
                break;
            default:
                plot = null;
                physics = null;
                break;
        }

        if (plot == null)
            return;

        generateAtoms(atoms, velocity, numberOfAtoms);

        arena.setVisible(true);
        arena.setAtoms(drawer);

        double sinceGasUpdate = 0;
        double sincePlotUpdate = 0;
        double gasTimer = System.currentTimeMillis();
        double plotTimer = System.currentTimeMillis();

        while (active) {
            sinceGasUpdate += System.currentTimeMillis() - gasTimer;
            sincePlotUpdate += System.currentTimeMillis() - plotTimer;
            gasTimer  = System.currentTimeMillis();
            plotTimer = System.currentTimeMillis();

            while (sinceGasUpdate > gasTPF) {
                physics.update(gasTPF);
                sinceGasUpdate -= gasTPF;
            }

            if (sincePlotUpdate > plotTPF) {
                plot.render();
                sincePlotUpdate = 0;
            }

            arena.pack();
            arena.repaint();
        }

        plot.dispose();
        arena.dispose();
    }



    public void stop() {
        active = false;
    }



    private void generateAtoms(List<Atom> atoms, int velocity, int numberOfAtoms) {
        Random random = new Random(System.currentTimeMillis());
        int v = (int)Math.floor(Math.sqrt(Math.pow(velocity, 2) / 2));
        for (int i = 0; i < numberOfAtoms; ++i) {
            int x = random.nextInt(WIDTH / 2);
            int y = random.nextInt(HEIGHT);
            Atom atom = new Atom(x, y, v, v);
            atoms.add(atom);
        }
    }
}