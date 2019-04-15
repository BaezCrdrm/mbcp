package mbcp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Mensaje;
import Model.Proceso;
 
/**
 * Ordenamiento usando el algoritmo
 * Minimal Broadcast Causal Protocol
 * 
 * @author BaezCrdrm
 */
public class Ordenamiento implements Runnable {
    private List<Proceso> procesos;
    private Map<Integer, Thread> hilos;

    public Ordenamiento(List<Proceso> procs)
    {
        this.procesos = new ArrayList<Proceso>();
        this.hilos = new HashMap<Integer, Thread>();
        for (Proceso proceso : procs) {
            if(proceso.esIniciable())
            {
                this.procesos.add(new Proceso(proceso, procs.size()));
            }
        }

        this.procesos.get(0).enviarMensaje(new Mensaje(1, 1, "Hola 1"), this.procesos.get(1));
        this.procesos.get(1).enviarMensaje(new Mensaje(2, 1, "Hola 2"), this.procesos.get(0));
    }

    public List<Mensaje> getOrden(int proceso)
    {
        try
        {
            return this.procesos.get(proceso).getOrden();
        }
        catch(NullPointerException npe) {
            System.out.println("Proceso " + proceso + " no válido");
        }
        return null;
    }

    @Override
    public void run() {
        // for(Proceso proceso : this.procesos)
        // {
        //     this.hilos.put(proceso.getId(), new Thread(proceso));
        //     this.hilos.get(proceso.getId()).start();
        // }
    }
}