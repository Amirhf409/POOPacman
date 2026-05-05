
public class FantasmaRondador extends Fantasma {
    private int turnosAleatorios;
    private static final int CICLO = 6;

    public FantasmaRondador(int x, int y) {
        super(x, y, 'P', "Pinky");
        this.turnosAleatorios = 0;
    }

    @Override
    public void mover(int dx, int dy, Tablero tablero) {

    }

    public void moverHacia(PacMan pacman, Tablero tablero) {
        int[] dir;

        if (isAsustado()) {
            dir = direccionAleatoria(tablero);
        } else if (turnosAleatorios > 0) {
            dir = direccionAleatoria(tablero);
            turnosAleatorios--;
        } else {

            int targetX = pacman.getX();
            int targetY = pacman.getY();
            dir = dirigirseA(targetX, targetY, tablero);

            if (Math.abs(getX() - pacman.getX()) + Math.abs(getY() - pacman.getY()) < 3) {
                turnosAleatorios = CICLO;
            }
        }

        int nx = getX() + dir[0];
        int ny = getY() + dir[1];
        if (tablero.esLibreParaFantasma(nx, ny)) {
            setX(nx);
            setY(ny);
        }
    }
}
