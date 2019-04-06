/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ark
 */
public class Mensaje extends Tupla implements Serializable
{
    private String cadena;
    private List<Tupla> historial;

    public Mensaje(int proceso, int numero, String cadena) {
        super(proceso, numero);
        initialize(cadena);
    }

    public Mensaje(Tupla tupla, String cadena) {
        super(tupla);
        initialize(cadena);
    }

    private void initialize(String cadena)
    {
        this.cadena = cadena;
        historial = new ArrayList<Tupla>();
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Tupla getTupla() { return (Tupla)this; }

    /**
     * @return the historial
     */
    public List<Tupla> getHistorial() {
        return historial;
    }

    /**
     * Enviar mensaje previamente construido
     */
    public void enviar(Proceso emisor, Proceso receptor)
    {
        this.historial = emisor.getCI();

        // TODO: Envío de mensaje por UDP.
    }

    @Override
    public String toString() {
        return "Mensaje{" + "numero=" + this.getMensaje() + ", cadena=" + cadena + ", vector=" + this.getProceso() + '}';
    }
    
}
