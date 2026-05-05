public abstract class Entidad {
    private int x;
    private int y;
    private char simbolo;

    public Entidad(int x, int y, char simbolo) {
        this.x = x;
        this.y = y;
        this.simbolo = simbolo;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public char getSimbolo() { return simbolo; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    protected void setSimbolo(char simbolo) { this.simbolo = simbolo; }

    public abstract void mover(int dx, int dy, Tablero tablero);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " en (" + x + ", " + y + ")";
    }
}
