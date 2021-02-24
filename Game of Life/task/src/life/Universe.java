package life;

class Universe {
    private Matrix currentGeneration;

    public Universe(int sizeOfUniverse) {
        currentGeneration = new Matrix(sizeOfUniverse);
        currentGeneration.populate();
    }

    public void generateNextGeneration() {
        currentGeneration = currentGeneration.nextGeneration();
    }

    public int getCellsAlive() {
        return currentGeneration.getTotalNumCellsAlive();
    }

    public boolean[] getGenerationStatus() {
        return currentGeneration.getStatus();
    }

    @Override
    public String toString() {
        return currentGeneration.toString();
    }
}