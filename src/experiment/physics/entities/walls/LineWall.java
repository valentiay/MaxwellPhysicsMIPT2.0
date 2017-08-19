package experiment.physics.entities.walls;

import experiment.physics.entities.Entity;
import experiment.physics.entities.atoms.Atom;
import java.util.List;

import java.awt.*;

public class LineWall implements Entity {
    private Color color = Color.yellow;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    public LineWall(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void update(double dt) {}

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }

    public void processAtoms(List<Atom> atoms) {
        // TODO
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
