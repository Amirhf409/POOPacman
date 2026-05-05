
public abstract class Personaje extends Entidad {
    private int vidas;
    private boolean activo;

    public Personaje(int x, int y, char simbolo, int vidas) {
        super(x, y, simbolo);
        this.vidas = vidas;
        this.activo = true;
    }

    public int getVidas() {
        return vidas;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void perderVida() {
        vidas--;
        if (vidas <= 0) {
            activo = false;
        }
    }

    public abstract void reiniciar(int x, int y);
}
