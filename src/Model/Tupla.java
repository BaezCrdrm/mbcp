package Model;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tupla
 */
public class Tupla implements Serializable{
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
        Tupla t = null;
        try
        {
            t = ci.stream().findAny().
                filter(p -> p.getProceso() == this.getProceso()).
                get();
        } catch(NoSuchElementException nsee)
        {
            return false;
        }

        return (t == null) ? false : true;
    }
}