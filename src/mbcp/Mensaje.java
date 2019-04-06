/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbcp;

import java.io.Serializable;

/**
 *
 * @author ark
 */
public class Mensaje implements Serializable{
    
    private int numero;
    private String cad;
    private int  vector;

    public Mensaje(int numero, String cad, int vector) {
        this.numero = numero;
        this.cad = cad;
        this.vector = vector;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCad() {
        return cad;
    }

    public void setCad(String cad) {
        this.cad = cad;
    }

    public int getVector() {
        return vector;
    }

    public void setVector(int vector) {
        this.vector = vector;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "numero=" + numero + ", cad=" + cad + ", vector=" + vector + '}';
    }
    
}
