package Model;

/**
 * Tupla
 */
public class Tupla {
    int proceso, mensaje;

    public Tupla(int proceso, int msg)
    {
        this.proceso = proceso;
        this.mensaje = msg;
    }

    public int getMensaje() { return this.mensaje; }

    public int getProceso() { return this.proceso; }
}