
public class Tablero {

    public static final char PARED = '#';
    public static final char VACIO = ' ';
    public static final char PUNTO = '.';
    public static final char SUPER_PUNTO = 'O';

    private char[][] mapa;
    private int filas;
    private int columnas;
    private int totalPuntos;

    private static final String[] MAPA_BASE = {
            "#####################",
            "#O........#........O#",
            "#.##.####.#.####.##.#",
            "#...................#",
            "#.##.#.#####.#.##..#",
            "#....#...#...#.....#",
            "####.###.#.###.#####",
            "   #.#         #.#  ",
            "####.# ##   ## #.###",
            "      .#       #.   ",
            "####.# ####### #.###",
            "   #.#         #.#  ",
            "####.# ####### #.###",
            "#O.......#.......O.#",
            "#.##.###.#.###.##..#",
            "#..#.....V.....#...#",
            "##.#.#.#####.#.#.###",
            "#....#...........#.#",
            "#####################"
    };

    public Tablero() {
        filas = MAPA_BASE.length;
        columnas = 21;
        mapa = new char[filas][columnas];
        totalPuntos = 0;
        cargarMapa();
    }

    private void cargarMapa() {
        for (int i = 0; i < filas; i++) {
            String fila = MAPA_BASE[i];
            for (int j = 0; j < columnas; j++) {
                char c = (j < fila.length()) ? fila.charAt(j) : VACIO;

                if (c == 'V')
                    c = VACIO;
                mapa[i][j] = c;
                if (c == PUNTO || c == SUPER_PUNTO) {
                    totalPuntos++;
                }
            }
        }
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public char getCelda(int x, int y) {
        return mapa[x][y];
    }

    public void setCelda(int x, int y, char c) {
        mapa[x][y] = c;
    }

    public void decrementarPuntos() {
        totalPuntos--;
    }

    public boolean esValido(int x, int y) {
        return x >= 0 && x < filas && y >= 0 && y < columnas;
    }

    public boolean esPared(int x, int y) {
        if (!esValido(x, y))
            return true;
        return mapa[x][y] == PARED;
    }

    public boolean esLibreParaFantasma(int x, int y) {
        if (!esValido(x, y))
            return false;
        return mapa[x][y] != PARED;
    }

    public void imprimir(PacMan pacman, Fantasma[] fantasmas) {
        char[][] display = new char[filas][columnas];
        for (int i = 0; i < filas; i++) {
            display[i] = mapa[i].clone();
        }

        for (Fantasma f : fantasmas) {
            if (f.isActivo()) {
                display[f.getX()][f.getY()] = f.getSimbolo();
            }
        }

        if (pacman.isActivo()) {
            display[pacman.getX()][pacman.getY()] = pacman.getSimbolo();
        }

        System.out.println("+" + "-".repeat(columnas) + "+");
        for (int i = 0; i < filas; i++) {
            System.out.print("|");
            for (int j = 0; j < columnas; j++) {
                System.out.print(display[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("+" + "-".repeat(columnas) + "+");
    }
}
