public class PacMan extends Personaje {
    private int puntaje;
    private int inicioX;
    private int inicioY;
    private boolean superPoder;
    private int turnosSuper;

    public PacMan(int x, int y) {
        super(x, y, 'C', 3);
        this.puntaje = 0;
        this.inicioX = x;
        this.inicioY = y;
        this.superPoder = false;
        this.turnosSuper = 0;
    }

    public int getPuntaje() { return puntaje; }
    public boolean isSuperPoder() { return superPoder; }
    public int getTurnosSuper() { return turnosSuper; }
    public void sumarPuntaje(int puntos) { puntaje += puntos; }

    public void activarSuperPoder(int turnos) {
        superPoder = true;
        turnosSuper = turnos;
        setSimbolo('@');
    }

    public void decrementarSuper() {
        if (superPoder) {
            turnosSuper--;
            if (turnosSuper <= 0) {
                superPoder = false;
                setSimbolo('C');
            }
        }
    }

    @Override
    public void mover(int dx, int dy, Tablero tablero) {
        int nx = getX() + dx;
        int ny = getY() + dy;

        if (tablero.esPared(nx, ny)) return;

        char celda = tablero.getCelda(nx, ny);

        if (celda == Tablero.PUNTO) {
            sumarPuntaje(10);
            tablero.setCelda(nx, ny, Tablero.VACIO);
            tablero.decrementarPuntos();
        } else if (celda == Tablero.SUPER_PUNTO) {
            sumarPuntaje(50);
            activarSuperPoder(8);
            tablero.setCelda(nx, ny, Tablero.VACIO);
            tablero.decrementarPuntos();
        }

        setX(nx);
        setY(ny);
        decrementarSuper();
    }

    @Override
    public void reiniciar(int x, int y) {
        setX(x != -1 ? x : inicioX);
        setY(y != -1 ? y : inicioY);
        superPoder = false;
        turnosSuper = 0;
        setSimbolo('C');
        setActivo(true);
    }
}
