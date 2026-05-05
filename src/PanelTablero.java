import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

// Panel que dibuja el tablero. Hereda de JPanel y sobreescribe paintComponent
public class PanelTablero extends JPanel {

    private static final int CELDA = 30; // tamaño en pixeles de cada celda

    private Tablero tablero;
    private PacMan pacman;
    private Fantasma[] fantasmas;

    public PanelTablero(Tablero tablero, PacMan pacman, Fantasma[] fantasmas) {
        this.tablero = tablero;
        this.pacman = pacman;
        this.fantasmas = fantasmas;

        int ancho = tablero.getColumnas() * CELDA;
        int alto = tablero.getFilas() * CELDA;
        setPreferredSize(new Dimension(ancho, alto));
        setBackground(Color.BLACK);
    }

    // Recibe los objetos actualizados y redibuja
    public void actualizar(Tablero tablero, PacMan pacman, Fantasma[] fantasmas) {
        this.tablero = tablero;
        this.pacman = pacman;
        this.fantasmas = fantasmas;
        repaint(); // le dice a Swing que vuelva a llamar paintComponent
    }

    // Swing llama este metodo cada vez que hay que redibujar
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
                    g.setColor(new Color(0, 0, 180)); // azul
                    g.fillRect(x, y, CELDA, CELDA);

                } else if (celda == Tablero.PUNTO) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, CELDA, CELDA);
                    g.setColor(Color.WHITE);
                    g.fillOval(x + 12, y + 12, 6, 6); // bolita pequeña

                } else if (celda == Tablero.SUPER_PUNTO) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, CELDA, CELDA);
                    g.setColor(Color.WHITE);
                    g.fillOval(x + 7, y + 7, 16, 16); // bolita grande

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

            // Color segun si esta asustado o no
            if (f.isAsustado()) {
                g.setColor(Color.BLUE);
            } else if (f.getSimbolo() == 'B') {
                g.setColor(Color.RED);   // Blinky
            } else {
                g.setColor(Color.PINK);  // Pinky
            }

            g.fillOval(x + 2, y + 2, CELDA - 4, CELDA - 4);
        }
    }

    private void dibujarPacMan(Graphics g) {
        if (!pacman.isActivo()) return;

        int x = pacman.getY() * CELDA;
        int y = pacman.getX() * CELDA;

        g.setColor(Color.YELLOW);
        g.fillArc(x + 2, y + 2, CELDA - 4, CELDA - 4, 30, 300); // circulo con boca
    }
}
