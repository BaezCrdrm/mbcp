package Model;

import java.util.List;

/**
 * Tupla
 */
public class Tupla {
    private int proceso, mensaje;

    public Tupla(int proceso, int msg)
    {
        this.proceso = proceso;
        this.mensaje = msg;
    }

    public Tupla(Tupla t)
    {
        this.proceso = t.getProceso();
        this.mensaje = t.getMensaje();
    }

    public Tupla(Mensaje msg)
    {
        this.proceso = msg.getProceso();
        this.mensaje = msg.getMensaje();
    }

    public int getMensaje() { return this.mensaje; }

    public int getProceso() { return this.proceso; }

    public boolean coincideCon(List<Tupla> ci)
    {
        Tupla t = ci.stream().findAny().
            filter(p -> p.getProceso() == this.getProceso()).
            get();

        return (t == null) ? false : true;
    }
}