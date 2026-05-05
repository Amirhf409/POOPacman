import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaJuego extends JFrame {

    private JuegoGUI juego;
    private PanelTablero panel;
    private JLabel etiquetaEstado;

    public VentanaJuego() {
        Tablero tableroTemp = new Tablero();
        PacMan pacmanTemp = new PacMan(16, 10);
        Fantasma[] fantasmasTemp = {
            new FantasmaPerseguidor(9, 9),
            new FantasmaRondador(9, 11)
        };

        panel = new PanelTablero(tableroTemp, pacmanTemp, fantasmasTemp);
        juego = new JuegoGUI(panel);
        juego.iniciar();

        configurarVentana();
        agregarComponentes();
        agregarTeclado();
    }

    private void configurarVentana() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void agregarComponentes() {
        etiquetaEstado = new JLabel("  Vidas: 3   Puntaje: 0");
        etiquetaEstado.setBackground(Color.BLACK);
        etiquetaEstado.setForeground(Color.YELLOW);
        etiquetaEstado.setOpaque(true);
        etiquetaEstado.setFont(new Font("Monospaced", Font.BOLD, 14));

        add(etiquetaEstado, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        Timer timerEtiqueta = new Timer(150, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarEtiqueta();
            }
        });
        timerEtiqueta.start();

        pack();
        setLocationRelativeTo(null);
    }

    private void agregarTeclado() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                juego.procesarTecla(e.getKeyCode());
            }
        });
        setFocusable(true);
    }

    private void actualizarEtiqueta() {
        PacMan p = juego.getPacman();
        String texto;

        if (!juego.isJugando()) {
            if (p.getVidas() <= 0) {
                texto = "  GAME OVER — Puntaje final: " + p.getPuntaje();
                etiquetaEstado.setForeground(Color.RED);
            } else {
                texto = "  GANASTE! — Puntaje: " + p.getPuntaje();
                etiquetaEstado.setForeground(Color.GREEN);
            }
        } else {
            texto = "  Vidas: " + p.getVidas()
                  + "   Puntaje: " + p.getPuntaje()
                  + "   Nivel: " + juego.getNivel();

            if (p.isSuperPoder()) {
                texto = texto + "   [SUPER: " + p.getTurnosSuper() + "]";
            }
        }

        etiquetaEstado.setText(texto);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VentanaJuego ventana = new VentanaJuego();
                ventana.setVisible(true);
            }
        });
    }
}
