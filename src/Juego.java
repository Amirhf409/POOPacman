import java.util.Scanner;

public class Juego {

    private Tablero tablero;
    private PacMan pacman;
    private Fantasma[] fantasmas;
    private Scanner scanner;
    private boolean jugando;
    private int nivel;

    private static final int PACMAN_X = 16;
    private static final int PACMAN_Y = 10;

    public Juego() {
        this.scanner = new Scanner(System.in);
        this.nivel = 1;
    }

    public void iniciar() {
        System.out.println("╔══════════════════════╗");
        System.out.println("║    P A C - M A N     ║");
        System.out.println("╠══════════════════════╣");
        System.out.println("║  W = arriba          ║");
        System.out.println("║  S = abajo           ║");
        System.out.println("║  A = izquierda       ║");
        System.out.println("║  D = derecha         ║");
        System.out.println("║  Q = salir           ║");
        System.out.println("╚══════════════════════╝");
        System.out.println("\nPresiona ENTER para comenzar...");
        scanner.nextLine();

        iniciarNivel();
        bucleJuego();
    }

    private void iniciarNivel() {
        tablero = new Tablero();
        pacman = new PacMan(PACMAN_X, PACMAN_Y);
        fantasmas = new Fantasma[] {
            new FantasmaPerseguidor(9, 9),
            new FantasmaRondador(9, 11)
        };
        jugando = true;
    }

    private void bucleJuego() {
        while (jugando) {
            limpiarPantalla();
            mostrarEstado();
            tablero.imprimir(pacman, fantasmas);

            String input = leerInput();
            if (input.equalsIgnoreCase("q")) {
                jugando = false;
                System.out.println("\nHasta luego!");
                break;
            }

            procesarInput(input);
            moverFantasmas();
            verificarColisiones();
            verificarVictoria();
        }

        if (pacman.getVidas() <= 0) {
            System.out.println("\n╔══════════════════════╗");
            System.out.println("║     GAME  OVER       ║");
            System.out.printf("║  Puntaje final: %4d ║%n", pacman.getPuntaje());
            System.out.println("╚══════════════════════╝");
        }
    }

    private String leerInput() {
        System.out.print("\nMovimiento (W/A/S/D, Q salir): ");
        return scanner.nextLine().trim();
    }

    private void procesarInput(String input) {
        if (input.isEmpty()) return;
        char tecla = Character.toUpperCase(input.charAt(0));

        if (tecla == 'W') {
            pacman.mover(-1, 0, tablero);
        } else if (tecla == 'S') {
            pacman.mover(1, 0, tablero);
        } else if (tecla == 'A') {
            pacman.mover(0, -1, tablero);
        } else if (tecla == 'D') {
            pacman.mover(0, 1, tablero);
        }
    }

    private void moverFantasmas() {
        boolean superActivo = pacman.isSuperPoder();

        for (Fantasma f : fantasmas) {
            if (!f.isActivo()) continue;
            f.setAsustado(superActivo);

            if (f instanceof FantasmaPerseguidor) {
                ((FantasmaPerseguidor) f).moverHacia(pacman, tablero);
            } else if (f instanceof FantasmaRondador) {
                ((FantasmaRondador) f).moverHacia(pacman, tablero);
            }
        }
    }

    private void verificarColisiones() {
        for (Fantasma f : fantasmas) {
            if (!f.isActivo()) continue;

            if (f.getX() == pacman.getX() && f.getY() == pacman.getY()) {
                if (pacman.isSuperPoder()) {
                    f.setActivo(false);
                    pacman.sumarPuntaje(200);
                } else {
                    pacman.perderVida();
                    if (pacman.isActivo()) {
                        pacman.reiniciar(-1, -1);
                        for (Fantasma reset : fantasmas) {
                            reset.reiniciar(-1, -1);
                            reset.setActivo(true);
                        }
                        pausa(1500);
                    } else {
                        jugando = false;
                    }
                }
            }
        }
    }

    private void verificarVictoria() {
        if (tablero.getTotalPuntos() <= 0) {
            limpiarPantalla();
            tablero.imprimir(pacman, fantasmas);
            System.out.println("\n╔══════════════════════╗");
            System.out.println("║    ¡ G A N A S T E ! ║");
            System.out.printf("║  Puntaje: %6d pts ║%n", pacman.getPuntaje());
            System.out.println("╚══════════════════════╝");
            jugando = false;
        }
    }

    private void mostrarEstado() {
        System.out.printf("  NIVEL: %d   PUNTAJE: %d   VIDAS: %d   PUNTOS: %d%n",
            nivel, pacman.getPuntaje(), pacman.getVidas(), tablero.getTotalPuntos());
        if (pacman.isSuperPoder()) {
            System.out.println("  *** SUPER PODER ACTIVO (" + pacman.getTurnosSuper() + " turnos) ***");
        }
        System.out.println();
    }

    private void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pausa(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
}
