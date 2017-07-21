package experiment.graphics.painters;

import experiment.Boltzmann;

import java.awt.*;


public class PainterKnudsen extends Painter {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2= (Graphics2D)g;
        g2.setColor(Color.ORANGE);
        g2.drawLine(Boltzmann.WIDTH / 2, 0, Boltzmann.WIDTH / 2, Boltzmann.HEIGHT / (Boltzmann.knudsenNumberOfHoles + 1) - Boltzmann.D);
        for (int i = 2; i < Boltzmann.knudsenNumberOfHoles + 1; i++) {
            g2.drawLine(Boltzmann.WIDTH / 2, (i - 1) * Boltzmann.HEIGHT / (Boltzmann.knudsenNumberOfHoles + 1) + Boltzmann.D,
                        Boltzmann.WIDTH / 2, i * Boltzmann.HEIGHT / (Boltzmann.knudsenNumberOfHoles + 1) - Boltzmann.D);
        }
        g2.drawLine(Boltzmann.WIDTH / 2,  Boltzmann.knudsenNumberOfHoles * Boltzmann.HEIGHT / (Boltzmann.knudsenNumberOfHoles + 1) + Boltzmann.D,
                Boltzmann.WIDTH / 2, Boltzmann.HEIGHT);
    }
}
