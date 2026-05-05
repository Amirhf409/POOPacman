import javax.swing.*;
import java.awt.*;

// Commit 9: Panel grafico que dibuja el tablero usando herencia de JPanel
public class PanelTablero extends JPanel {

    private static final int CELDA = 32; // pixeles por celda

    private Tablero tablero;
    private PacMan pacman;
    private Fantasma[] fantasmas;

    public PanelTablero(Tablero tablero, PacMan pacman, Fantasma[] fantasmas) {
        this.tablero = tablero;
        this.pacman = pacman;
        this.fantasmas = fantasmas;
        int ancho = tablero.getColumnas() * CELDA;
        int alto  = tablero.getFilas()    * CELDA;
        setPreferredSize(new Dimension(ancho, alto));
        setBackground(Color.BLACK);
    }

    // Actualiza las referencias y redibuja
    public void actualizar(Tablero tablero, PacMan pacman, Fantasma[] fantasmas) {
        this.tablero   = tablero;
        this.pacman    = pacman;
        this.fantasmas = fantasmas;
        repaint();
    }

    // Polimorfismo: sobreescribe paintComponent de JComponent
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        dibujarTablero(g2);
        dibujarFantasmas(g2);
        dibujarPacMan(g2);
    }

    private void dibujarTablero(Graphics2D g2) {
        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {
                char celda = tablero.getCelda(fila, col);
                int px = col  * CELDA;
                int py = fila * CELDA;

                if (celda == Tablero.PARED) {
                    // Pared azul con borde mas claro
                    g2.setColor(new Color(0, 0, 180));
                    g2.fillRect(px, py, CELDA, CELDA);
                    g2.setColor(new Color(80, 80, 255));
                    g2.drawRect(px, py, CELDA - 1, CELDA - 1);

                } else {
                    // Fondo negro
                    g2.setColor(Color.BLACK);
                    g2.fillRect(px, py, CELDA, CELDA);

                    if (celda == Tablero.PUNTO) {
                        g2.setColor(new Color(255, 200, 150));
                        int tam = 6;
                        g2.fillOval(px + CELDA / 2 - tam / 2,
                                    py + CELDA / 2 - tam / 2, tam, tam);

                    } else if (celda == Tablero.SUPER_PUNTO) {
                        g2.setColor(new Color(255, 200, 150));
                        int tam = 14;
                        g2.fillOval(px + CELDA / 2 - tam / 2,
                                    py + CELDA / 2 - tam / 2, tam, tam);
                    }
                }
            }
        }
    }

    private void dibujarFantasmas(Graphics2D g2) {
        for (Fantasma f : fantasmas) {
            if (!f.isActivo()) continue;

            int px = f.getY() * CELDA;
            int py = f.getX() * CELDA;

            // Color segun estado (polimorfismo visual)
            if (f.isAsustado()) {
                g2.setColor(new Color(0, 0, 200));
            } else {
                g2.setColor(f.getSimbolo() == 'B' ? Color.RED : Color.PINK);
            }

            // Cuerpo: semicirculo arriba + rectangulo abajo
            g2.fillArc(px + 2, py + 2, CELDA - 4, CELDA - 4, 0, 180);
            g2.fillRect(px + 2, py + CELDA / 2, CELDA - 4, CELDA / 2 - 2);

            // Ojos blancos
            g2.setColor(Color.WHITE);
            g2.fillOval(px + 6,  py + 9,  7, 8);
            g2.fillOval(px + 18, py + 9,  7, 8);

            // Pupilas azules
            g2.setColor(f.isAsustado() ? Color.RED : Color.BLUE);
            g2.fillOval(px + 8,  py + 11, 4, 5);
            g2.fillOval(px + 20, py + 11, 4, 5);
        }
    }

    private void dibujarPacMan(Graphics2D g2) {
        if (!pacman.isActivo()) return;

        int px = pacman.getY() * CELDA;
        int py = pacman.getX() * CELDA;

        // Super poder: amarillo brillante; normal: amarillo
        g2.setColor(pacman.isSuperPoder() ? new Color(255, 220, 0) : Color.YELLOW);
        g2.fillArc(px + 2, py + 2, CELDA - 4, CELDA - 4, 30, 300);
    }
}
