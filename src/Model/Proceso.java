package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Proceso
 */
public class Proceso
{
    String address, port, id;
    List<Mensaje> buffer, recibidos;
    List<Proceso> procesos;
    List<Tupla> ci;
    int[] vt;


    public Proceso(String ip, String port, String id)
    {
        this.address = ip;
        this.port = port;
        this.id = id;
    }

    public Proceso(List<Proceso> procesos, String ip)
    {
        this.buffer = new ArrayList<Mensaje>();
        this.procesos = procesos;
        this.vt = new int[procesos.size()];
        this.address = ip;
        this.ci = new ArrayList<Tupla>();
    }
}