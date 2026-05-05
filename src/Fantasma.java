
public abstract class Fantasma extends Personaje {
    private int inicioX;
    private int inicioY;
    private boolean asustado;
    private String nombre;
    private char simboloOriginal;   // guarda el símbolo antes de asustarse

    public Fantasma(int x, int y, char simbolo, String nombre) {
        super(x, y, simbolo, 1);
        this.inicioX = x;
        this.inicioY = y;
        this.asustado = false;
        this.nombre = nombre;
        this.simboloOriginal = simbolo;
    }

    // Getters y Setters
    public boolean isAsustado() {
        return asustado;
    }

    public String getNombre() {
        return nombre;
    }

    public int getInicioX() {
        return inicioX;
    }

    public int getInicioY() {
        return inicioY;
    }

    public void setAsustado(boolean asustado) {
        this.asustado = asustado;
        // Al asustarse usa 'F', al recuperarse vuelve a su símbolo original
        setSimbolo(asustado ? 'F' : simboloOriginal);
    }

    @Override
    public abstract void mover(int dx, int dy, Tablero tablero);

    protected int[] calcularDireccion(PacMan pacman, Tablero tablero) {

        if (asustado) {
            return direccionAleatoria(tablero);
        }
        return dirigirseA(pacman.getX(), pacman.getY(), tablero);
    }

    protected int[] dirigirseA(int targetX, int targetY, Tablero tablero) {
        int dx = Integer.compare(targetX, getX());
        int dy = Integer.compare(targetY, getY());

        int[][] opciones = {
                { dx, 0 }, { 0, dy }, { -dx, 0 }, { 0, -dy }
        };

        for (int[] op : opciones) {
            int nx = getX() + op[0];
            int ny = getY() + op[1];
            if (tablero.esLibreParaFantasma(nx, ny)) {
                return op;
            }
        }
        return new int[] { 0, 0 };
    }

    protected int[] direccionAleatoria(Tablero tablero) {
        int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        int inicio = (getX() + getY() + (int) (System.currentTimeMillis() % 4)) % 4;
        for (int i = 0; i < 4; i++) {
            int[] d = dirs[(inicio + i) % 4];
            if (tablero.esLibreParaFantasma(getX() + d[0], getY() + d[1])) {
                return d;
            }
        }
        return new int[] { 0, 0 };
    }

    @Override
    public void reiniciar(int x, int y) {
        setX(inicioX);
        setY(inicioY);
        asustado = false;
        setSimbolo(simboloOriginal);
        setActivo(true);
    }
}
