package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Proceso
 */
public class Proceso
{
    private String address, puerto;
    private int id;
    private List<Mensaje> buffer, recibidos;
    private List<Tupla> ci;
    private int[] vt;

    /**
     * Crea la definición de un proceso
     * @param ip Dirección IP del equipo que ejecutará el proceso
     * @param puerto Puerto del equipo que ejecutará el proceso
     * @param id Identificador del proceso
     */
    public Proceso(String ip, String puerto, int id)
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
    public int getId() {
        return id;
    }

    public List<Tupla> getCI() { return this.ci; }

    public boolean esIniciable()
    {
        if(!((this.address == null || this.address.equals("")) &&
            (this.puerto == null || this.puerto.equals(""))))
            return true;
        else return false;
    }

    /**
     * Actualiza el reloj lógico del proceso.
     * @param tupla Tupla que contiene los valores del mensaje
     */
    public void actualizaVector(Tupla tupla)
    {
        this.vt[tupla.getProceso()] = tupla.getMensaje();
    }

    /**
     * Actualiza el reloj lógico del proceso.
     * @param proceso ID del proceso
     * @param mensaje Número de mensaje
     */
    public void actualizaVector(int proceso, int mensaje)
    {
        this.vt[proceso] = mensaje;
    }

    /**
     * Actualiza el reloj lógico del proceso.
     * @param proceso ID del proceso
     * 
     */
    public void actualizaVector(int proceso)
    {
        this.vt[proceso] += 1;
    }

    public boolean comparaEnHistorial(List<Tupla> historial)
    {
        for (Tupla tupla : historial)
        {
            if(!(tupla.getMensaje() <= this.vt[tupla.getProceso()]))
                return false;
        }
        return true;
    }

    public void delivery(Mensaje msg)
    {
        this.vt[id] += 1;
        if(msg.coincideCon(this.ci))
            poda();
        else
        {
            ci.add((Tupla)msg);
            poda(msg.getHistorial());
        }
    }

    /**
     * Remueve Tuplas de CI del proceso con base
     * en el historial dado por el mensaje
     */
    private void poda()
    {
        this.ci.remove(
            ci.stream().findAny().
            filter(p -> p.getProceso() == this.id).
            get());
    }

    /**
     * Remueve Tuplas de CI del proceso con base
     * en el historial dado por el mensaje
     * @param historial
     */
    private void poda(List<Tupla> historial)
    {
        for (Tupla tci : this.ci)
        {
            for(Tupla his : historial)
            {
                if(tci.getProceso() == his.getProceso())
                    this.ci.remove(tci);
            }
        }
    }
}