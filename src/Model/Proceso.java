package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Proceso
 */
public class Proceso
{
    String address, puerto, id;
    List<Mensaje> buffer, recibidos;
    List<Tupla> ci;
    int[] vt;

    /**
     * Crea la definición de un proceso
     * @param ip Dirección IP del equipo que ejecutará el proceso
     * @param puerto Puerto del equipo que ejecutará el proceso
     * @param id Identificador del proceso
     */
    public Proceso(String ip, String puerto, String id)
    {
        this.address = ip;
        this.puerto = puerto;
        this.id = id;
    }

    /**
     * Crea un nuevo proceso que será utilizado en el
     * algoritmo
     * @param proceso Proceso de definición del cual se obtendrán
     * los datos del proceso
     * @param n Número de procesos. Creará el vector de tamaño 'n'
     */
    public Proceso(Proceso proceso, int n)
    {
        try
        {
            this.id = proceso.getId();
            this.address = proceso.getAddress();
            this.puerto = proceso.getPuerto();

            this.buffer = new ArrayList<Mensaje>();
            this.vt = new int[n];
            this.ci = new ArrayList<Tupla>();
        } catch(NullPointerException npe)
        {
            System.out.println("Ocurró un error al crear el proceso");
            System.out.println("Agrega los valores correctos al parámetro");
            throw new NullPointerException();
        }
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the puerto
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    public boolean esIniciable()
    {
        if(!((this.id == null || this.id.equals("")) &&
            (this.address == null || this.address.equals("")) &&
            (this.puerto == null || this.puerto.equals(""))))
            return true;
        else return false;
    }

    /**
     * 
     * @param t
     */
    public void actualizaVector(Tupla t)
    {
        this.vt[t.proceso] = t.mensaje;
    }

    public void actualizaVector(int proceso, int mensaje)
    {
        this.vt[proceso] = mensaje;
    }
}