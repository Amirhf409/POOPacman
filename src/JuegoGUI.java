import javax.swing.Timer;
import java.awt.event.KeyEvent;

// Commit 10: Logica del juego adaptada para GUI — usa Timer de Swing en vez de Scanner
public class JuegoGUI {

    private Tablero tablero;
    private PacMan pacman;
    private Fantasma[] fantasmas;
    private boolean jugando;
    private int nivel;

    // Direccion actual de PacMan (se actualiza con el teclado)
    private int dx;
    private int dy;

    private static final int PACMAN_X  = 16;
    private static final int PACMAN_Y  = 10;
    private static final int DELAY_MS  = 200; // ms entre cada tick del juego

    private PanelTablero panel;
    private Timer timer;

    public JuegoGUI(PanelTablero panel) {
        this.panel  = panel;
        this.nivel  = 1;
        this.dx     = 0;
        this.dy     = 0;
    }

    // Inicializa el tablero y arranca el Timer
    public void iniciar() {
        iniciarNivel();
        panel.actualizar(tablero, pacman, fantasmas);

        // Cada DELAY_MS milisegundos se ejecuta un tick del juego
        timer = new Timer(DELAY_MS, e -> tick());
        timer.start();
    }

    private void iniciarNivel() {
        tablero   = new Tablero();
        pacman    = new PacMan(PACMAN_X, PACMAN_Y);
        fantasmas = new Fantasma[] {
            new FantasmaPerseguidor(9, 9),
            new FantasmaRondador(9, 11)
        };
        jugando = true;
    }

    // Un paso del bucle de juego — equivalente a una iteracion del while en Juego.java
    private void tick() {
        if (!jugando) {
            timer.stop();
            return;
        }

        pacman.mover(dx, dy, tablero);
        moverFantasmas();
        verificarColisiones();
        verificarVictoria();

        panel.actualizar(tablero, pacman, fantasmas);
    }

    // Recibe el codigo de tecla desde VentanaJuego y actualiza la direccion
    public void procesarTecla(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W: case KeyEvent.VK_UP:    dx = -1; dy =  0; break;
            case KeyEvent.VK_S: case KeyEvent.VK_DOWN:  dx =  1; dy =  0; break;
            case KeyEvent.VK_A: case KeyEvent.VK_LEFT:  dx =  0; dy = -1; break;
            case KeyEvent.VK_D: case KeyEvent.VK_RIGHT: dx =  0; dy =  1; break;
        }
    }

    // Polimorfismo: se llama moverHacia segun el tipo real de cada fantasma
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
                        dx = 0;
                        dy = 0;
                    } else {
                        jugando = false;
                    }
                }
            }
        }
    }

    private void verificarVictoria() {
        if (tablero.getTotalPuntos() <= 0) {
            jugando = false;
        }
    }

    // Getters para que VentanaJuego pueda leer el estado y mostrarlo
    public boolean isJugando()  { return jugando; }
    public PacMan  getPacman()  { return pacman;  }
    public int     getNivel()   { return nivel;   }
}
