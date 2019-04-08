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
    private String datos;
    private List<Tupla> historial;

    public Mensaje(int proceso, int numero, String datos) {
        super(proceso, numero);
        initialize(datos);
    }

    public Mensaje(Tupla tupla, String datos) {
        super(tupla);
        initialize(datos);
    }

    private void initialize(String datos)
    {
        this.datos = datos;
        historial = new ArrayList<Tupla>();
    }

    /**
     * Obtener datos
     * @return
     */
    public String getCadena() {
        return datos;
    }

    public void setCadena(String datos) {
        this.datos = datos;
    }

    public Tupla getTupla() { return new Tupla(this); }

    /**
     * @return the historial
     */
    public List<Tupla> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Tupla> ci)
    {
        this.historial = ci;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "numero=" + this.getMensaje() + ", datos=" + datos + ", vector=" + this.getProceso() + '}';
    }
    
}
