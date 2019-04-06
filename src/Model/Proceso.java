package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Proceso
 */
public class Proceso
{
    String address, port, id;
    List<Object> buffer;
    List<Proceso> procesos;
    List<Tupla> ci;
    int[] vt;


    public Proceso(String ip, String port, String id)
    {
        initialize(ip);
        this.port = port;
        this.id = id;
    }

    public Proceso(List<Proceso> procesos, String ip)
    {
        initialize(ip);
        this.buffer = new ArrayList<Object>();
        this.procesos = procesos;
        this.vt = new int[procesos.size()];
    }

    private void initialize(String ip)
    {
        this.address = ip;
        this.ci = new ArrayList<Tupla>();
    }
}