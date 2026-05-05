public class FantasmaPerseguidor extends Fantasma {

    public FantasmaPerseguidor(int x, int y) {
        super(x, y, 'B', "Blinky");
    }

    @Override
    public void mover(int dx, int dy, Tablero tablero) {
    }

    public void moverHacia(PacMan pacman, Tablero tablero) {
        int[] dir = calcularDireccion(pacman, tablero);
        int nx = getX() + dir[0];
        int ny = getY() + dir[1];
        if (tablero.esLibreParaFantasma(nx, ny)) {
            setX(nx);
            setY(ny);
        }
    }
}
