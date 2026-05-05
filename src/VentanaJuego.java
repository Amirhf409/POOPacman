import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Commit 11: Ventana principal — conecta PanelTablero, JuegoGUI y teclado
public class VentanaJuego extends JFrame {

    private static final String TITULO = "Pac-Man — POO";

    private JuegoGUI     juego;
    private PanelTablero panel;
    private JLabel       lblEstado;

    public VentanaJuego() {
        // Objetos iniciales para construir el panel antes de iniciar el juego
        Tablero    tableroTemp   = new Tablero();
        PacMan     pacmanTemp    = new PacMan(16, 10);
        Fantasma[] fantasmasTemp = {
            new FantasmaPerseguidor(9, 9),
            new FantasmaRondador(9, 11)
        };

        panel = new PanelTablero(tableroTemp, pacmanTemp, fantasmasTemp);
        juego = new JuegoGUI(panel);

        // Primero iniciamos el juego (crea los objetos reales y actualiza el panel)
        juego.iniciar();

        // Luego armamos la ventana con el panel ya listo
        configurarVentana();
        agregarComponentes();
        agregarTeclado();
    }

    private void configurarVentana() {
        setTitle(TITULO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void agregarComponentes() {
        // Etiqueta de estado en la parte superior
        lblEstado = new JLabel("  Vidas: ♥♥♥   Puntaje: 0   Nivel: 1",
                               SwingConstants.LEFT);
        lblEstado.setBackground(Color.BLACK);
        lblEstado.setForeground(Color.YELLOW);
        lblEstado.setOpaque(true);
        lblEstado.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        add(lblEstado, BorderLayout.NORTH);
        add(panel,     BorderLayout.CENTER);

        // Timer que refresca la etiqueta cada 100 ms
        Timer labelTimer = new Timer(100, e -> actualizarEtiqueta());
        labelTimer.start();

        pack();
        setLocationRelativeTo(null); // centrar en pantalla
    }

    private void agregarTeclado() {
        // KeyAdapter: herencia de adaptador, solo sobreescribimos lo que necesitamos
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                juego.procesarTecla(e.getKeyCode());
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }

    private void actualizarEtiqueta() {
        PacMan p     = juego.getPacman();
        int    vidas = Math.max(0, p.getVidas());
        String corazones = "♥".repeat(vidas);

        String texto;
        if (!juego.isJugando()) {
            if (p.getVidas() <= 0) {
                texto = "  ★ GAME OVER ★   Puntaje final: " + p.getPuntaje();
                lblEstado.setForeground(Color.RED);
            } else {
                texto = "  ★ ¡GANASTE! ★   Puntaje: " + p.getPuntaje()
                      + "   Vidas: " + corazones;
                lblEstado.setForeground(Color.GREEN);
            }
        } else {
            String super_ = p.isSuperPoder()
                ? "   [SUPER " + p.getTurnosSuper() + " turnos]" : "";
            texto = "  Vidas: " + corazones
                  + "   Puntaje: " + p.getPuntaje()
                  + "   Nivel: "   + juego.getNivel()
                  + super_;
            lblEstado.setForeground(Color.YELLOW);
        }
        lblEstado.setText(texto);
    }

    // Punto de entrada de la version con interfaz grafica
    public static void main(String[] args) {
        // invokeLater garantiza que la UI se crea en el hilo de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            VentanaJuego ventana = new VentanaJuego();
            ventana.setVisible(true);
        });
    }
}
