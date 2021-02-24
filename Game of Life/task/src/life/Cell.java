package life;

class Cell {
    private final State isAlive;

    public Cell(State isAlive) {
        this.isAlive = isAlive;
    }

    public State isAlive() {
        return isAlive;
    }

    @Override
    public String toString() {
        if (isAlive == State.ALIVE) {
            return "O";
        }
        return " ";
    }
}