import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class PanelTablero extends JPanel {

    private static final int CELDA = 30;

    private Tablero tablero;
    private PacMan pacman;
    private Fantasma[] fantasmas;

    public PanelTablero(Tablero tablero, PacMan pacman, Fantasma[] fantasmas) {
        this.tablero = tablero;
        this.pacman = pacman;
        this.fantasmas = fantasmas;
        setPreferredSize(new Dimension(tablero.getColumnas() * CELDA, tablero.getFilas() * CELDA));
        setBackground(Color.BLACK);
    }

    public void actualizar(Tablero tablero, PacMan pacman, Fantasma[] fantasmas) {
        this.tablero = tablero;
        this.pacman = pacman;
        this.fantasmas = fantasmas;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarTablero(g);
        dibujarFantasmas(g);
        dibujarPacMan(g);
    }

    private void dibujarTablero(Graphics g) {
        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {
                char celda = tablero.getCelda(fila, col);
                int x = col * CELDA;
                int y = fila * CELDA;

                if (celda == Tablero.PARED) {
                    g.setColor(new Color(0, 0, 180));
                    g.fillRect(x, y, CELDA, CELDA);
                } else if (celda == Tablero.PUNTO) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, CELDA, CELDA);
                    g.setColor(Color.WHITE);
                    g.fillOval(x + 12, y + 12, 6, 6);
                } else if (celda == Tablero.SUPER_PUNTO) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, CELDA, CELDA);
                    g.setColor(Color.WHITE);
                    g.fillOval(x + 7, y + 7, 16, 16);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, CELDA, CELDA);
                }
            }
        }
    }

    private void dibujarFantasmas(Graphics g) {
        for (Fantasma f : fantasmas) {
            if (!f.isActivo()) continue;

            int x = f.getY() * CELDA;
            int y = f.getX() * CELDA;

            if (f.isAsustado()) {
                g.setColor(Color.BLUE);
            } else if (f.getSimbolo() == 'B') {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.PINK);
            }

            g.fillOval(x + 2, y + 2, CELDA - 4, CELDA - 4);
        }
    }

    private void dibujarPacMan(Graphics g) {
        if (!pacman.isActivo()) return;

        int x = pacman.getY() * CELDA;
        int y = pacman.getX() * CELDA;

        g.setColor(Color.YELLOW);
        g.fillArc(x + 2, y + 2, CELDA - 4, CELDA - 4, 30, 300);
    }
}
