package experiment.graphics.painters;

import experiment.physics.entities.Entity;

import java.util.List;
import java.awt.*;



public abstract class Painter {

    public void paint(Graphics g) {}
    public void paint(Graphics g, List<Entity> objects) {}
}