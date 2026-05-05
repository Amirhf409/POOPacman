import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class JuegoGUI {

    private Tablero tablero;
    private PacMan pacman;
    private Fantasma[] fantasmas;
    private boolean jugando;
    private int nivel;
    private int dx;
    private int dy;

    private static final int PACMAN_X = 16;
    private static final int PACMAN_Y = 10;
    private static final int VELOCIDAD = 200;

    private PanelTablero panel;
    private Timer timer;

    public JuegoGUI(PanelTablero panel) {
        this.panel = panel;
        this.nivel = 1;
        this.dx = 0;
        this.dy = 0;
    }

    public void iniciar() {
        iniciarNivel();

        timer = new Timer(VELOCIDAD, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();
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

    public void procesarTecla(int keyCode) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            dx = -1; dy = 0;
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            dx = 1; dy = 0;
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            dx = 0; dy = -1;
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            dx = 0; dy = 1;
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

    public boolean isJugando() { return jugando; }
    public PacMan getPacman()  { return pacman; }
    public int getNivel()      { return nivel; }
}
