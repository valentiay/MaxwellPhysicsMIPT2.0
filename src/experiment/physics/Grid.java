package experiment.physics;

import experiment.exceptions.GridException;
import experiment.physics.entities.atoms.Atom;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class used for optimizing collision processing. Scene is split
 * into squares called cells. Cell contains a list of atoms,
 * which centers are inside the related square.
 */
public class Grid {
    /**
     * Cell side size in pixels.
     */
    private final int cellSize;

    /** Number of horizontal cells in grid. */
    private final int gridWidth;

    /** Number of vertical cells in grid. */
    private final int gridHeight;

    /**
     * Getter for cellSize.
     *
     * @return cellSize value.
     * @see Grid#cellSize
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * Getter for gridWidth.
     *
     * @return gridWidth value.
     * @see Grid#gridWidth
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * Setter for gridHeight
     *
     * @return gridHeight value.
     */
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * Double-dimensional array of Linked lists representing atoms in
     * cell having indices equal to array ones.
     */
    private final ArrayList<ArrayList<LinkedList<Atom>>> grid;

    public Grid(int width, int height, int cellSize) {
        this.cellSize = cellSize;
        this.gridHeight = height / this.cellSize + 1;
        this.gridWidth = width / this.cellSize + 1;

        if (gridWidth < 1 || gridHeight < 1) {
            throw new GridException("Invalid grid parameters: cell size too big");
        }

        this.grid = new ArrayList<>(gridWidth);
        for (int i = 0; i < gridWidth; i++) {
            grid.add(i, new ArrayList<>(gridHeight));
            for (int j = 0; j < gridHeight; j++) {
                grid.get(i).add(j, new LinkedList<>());
            }
        }
    }

    /**
     * Builds the grid. Atoms are put into cells depending on their coordinates.
     * Some of the atoms may be out of the scene so they wouldn't be put into
     * the grid and their collisions would not be processed.
     *
     * @param atoms list of atoms.
     * @see Atom
     */
    public void build(List<Atom> atoms) {
        clear();
        for (Atom atom : atoms) {
            addAtom(atom);
        }
    }

    /**
     * Clears all the cells.
     */
    public void clear() {
        for (ArrayList<LinkedList<Atom>> col : grid) {
            for (LinkedList<? extends Atom> atomsList : col) {
                atomsList.clear();
            }
        }
    }

    /**
     * gets list of atoms in the requested cell.
     *
     * @param posX x position of the cell.
     * @param posY y position of the cell.
     * @return list of atoms int the cell having this position.
     */
    public List<Atom> getAt(int posX, int posY) {
        return grid.get(posX).get(posY);
    }

    /**
     * Returns list of atoms contained in neighbour cells. Major method to optimize
     * collision processing. <b>It is expected that cells would be processed top-left
     * corner line by line, so functions is returning atoms only in current cell,
     * right, right-bottom, bottom, and left-bottom cells.</b>
     *
     * @param posX x position of the cell.
     * @param posY y position of the cell.
     * @return list of atoms in <b>current cell, right, right-bottom, and bottom cells.</b>
     */
    public List<Atom> getNeighbourAtoms(int posX, int posY) {
        List<Atom> neighbours = new LinkedList<>();

        if (posX < 0 || posX >= gridWidth || posY < 0 || posY >= gridHeight) {
            throw new GridException("Index out of grid bounds");
        }

        if (posX > 0) {
            if (posY < gridHeight - 1) {
                neighbours.addAll(getAt(posX - 1, posY + 1));
            }
        }
        neighbours.addAll(getAt(posX, posY));
        if (posY < gridHeight - 1) {
            neighbours.addAll(getAt(posX, posY + 1));
        }
        if (posX < gridWidth - 1) {
            neighbours.addAll(getAt(posX + 1, posY));
            if (posY < gridHeight - 1) {
                neighbours.addAll(getAt(posX + 1, posY + 1));
            }
        }

        return neighbours;
    }

    /**
     * Adds atom into the grid. If atom is out of grid bounds it is ignored.
     *
     * @param atom atom to insert into the grid.
     */
    public void addAtom(Atom atom) {
        int x = (int)atom.x / cellSize;
        int y = (int)atom.y / cellSize;
        if (x >= 0 && y >= 0 && x < gridWidth && y < gridHeight) {
            grid.get(x).get(y).add(atom);
        }
    }

    /**
     * Dumps grid into standard output. Gird is printed as table, number of atoms
     * in each cell is represented. Total number of atoms in the grid is also displayed.
     */
    @Deprecated
    public void dump() {
        int totalN = 0;
        for (int j = 0; j < gridHeight; j++) {
            for (int i = 0; i < gridWidth; i++) {
                System.out.print(grid.get(i).get(j).size() + " ");
                totalN += grid.get(i).get(j).size();
            }
            System.out.println();
        }
        System.out.println("ttl" + totalN);
    }
}
