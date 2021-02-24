package life;

import java.util.Random;

class Matrix {
    private final Cell[][] matrix;
    private final int sizeOfUniverse;

    public Matrix(int sizeOfUniverse) {
        this.sizeOfUniverse = sizeOfUniverse;
        this.matrix = new Cell[sizeOfUniverse][sizeOfUniverse];
    }

    public void populate() {
        Random random = new Random();
        for (int i = 0; i < sizeOfUniverse; i++) {
            for (int j = 0; j < sizeOfUniverse; j++) {
                matrix[i][j] = new Cell(random.nextBoolean() ? State.ALIVE : State.DEAD);
            }
        }
    }

    public Matrix nextGeneration() {
        Matrix newGeneration = new Matrix(sizeOfUniverse);
        Cell cell;

        for (int i = 0; i < sizeOfUniverse; i++) {
            for (int j = 0; j < sizeOfUniverse; j++) {
                cell = matrix[i][j];
                if (cell != null) {
                    State isAlive = nextEvolutionForCell(new Position(i, j), cell.isAlive());
                    newGeneration.addCell(i, j, isAlive);
                }
            }
        }
        return newGeneration;
    }

    public void addCell(int i, int j, State isAlive) {
        matrix[i][j] = new Cell(isAlive);
    }

    public State nextEvolutionForCell(Position currentPosition, State isAlive) {
        if (isAlive == State.ALIVE) {
            // An alive cell survives if has two or three alive neighbors;
            // otherwise, it dies of boredom (<2) or overpopulation (>3)
            if (getNumberOfNeighborsFor(currentPosition) == 2 || getNumberOfNeighborsFor(currentPosition) == 3) {
                return State.ALIVE;
            } else {
                return State.DEAD;
            }
        } else {
            // A dead cell is reborn if it has exactly three alive neighbors
            if (getNumberOfNeighborsFor(currentPosition) == 3) {
                return State.ALIVE;
            } else {
                return State.DEAD;
            }
        }
    }

    public int getTotalNumCellsAlive() {
        int numTotalCellsAlive = 0;

        for (int i = 0; i < sizeOfUniverse; i++) {
            for (int j = 0; j < sizeOfUniverse; j++) {
                if (matrix[i][j].isAlive() == State.ALIVE) {
                    numTotalCellsAlive++;
                }
            }
        }
        return numTotalCellsAlive;
    }

    public int getNumberOfNeighborsFor(Position currentPosition) {
        int numberOfNeighbors = 0;
        int row;
        int col;
        Position[] adjacentPositions = getAdjacentPositions(currentPosition);
        for (Position adjacentPosition : adjacentPositions) {
            row = adjacentPosition.getRow();
            col = adjacentPosition.getCol();
            if (matrix[row][col] != null && matrix[row][col].isAlive() == State.ALIVE) {
                numberOfNeighbors++;
            }
        }
        return numberOfNeighbors;
    }

    public Position[] getAdjacentPositions(Position currentPosition) {
        Position nw, n, ne, e, se, s, sw, w;
        int row = currentPosition.getRow();
        int col = currentPosition.getCol();
        nw = new Position(row == 0 ? sizeOfUniverse - 1 : row - 1, col == 0 ? sizeOfUniverse - 1 : col - 1);
        n = new Position(row == 0 ? sizeOfUniverse - 1 : row - 1, col);
        ne = new Position(row == 0 ? sizeOfUniverse - 1 : row - 1, col == sizeOfUniverse - 1 ? 0 : col + 1);
        e = new Position(row, col == sizeOfUniverse - 1 ? 0 : col + 1);
        se = new Position(row == sizeOfUniverse - 1 ? 0 : row + 1, col == sizeOfUniverse - 1 ? 0 : col + 1);
        s = new Position(row == sizeOfUniverse - 1 ? 0 : row + 1, col);
        sw = new Position(row == sizeOfUniverse - 1 ? 0 : row + 1, col == 0 ? sizeOfUniverse - 1 : col - 1);
        w = new Position(row, col == 0 ? sizeOfUniverse - 1 : col - 1);
        return new Position[]{nw, n, ne, e, se, s, sw, w};
    }

    public boolean[] getStatus () {
        boolean[] status = new boolean[sizeOfUniverse * sizeOfUniverse];
        int cont = 0;
        for (int i = 0; i < sizeOfUniverse; i++) {
            for (int j = 0; j < sizeOfUniverse; j++) {
                status[cont++] = matrix[i][j].isAlive() == State.ALIVE ? true : false;
            }
        }
        return status;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();
        for (int i = 0; i < sizeOfUniverse; i++) {
            for (int j = 0; j < sizeOfUniverse; j++) {
                strB.append(matrix[i][j]);
            }
            strB.append("\n");
        }
        return strB.toString();
    }
}