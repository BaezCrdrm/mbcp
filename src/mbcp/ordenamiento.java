package mbcp;

import java.util.ArrayList;
import java.util.List;

import Model.Proceso;
 
/**
 * Ordenamiento usando el algoritmo
 * Minimal Broadcast Causal Protocol
 */
public class Ordenamiento {
    private List<Proceso> procesos;

    public Ordenamiento(List<Proceso> procs)
    {
        this.procesos = new ArrayList<Proceso>();
        for (Proceso proceso : procs) {
            if(proceso.esIniciable())
                this.procesos.add(new Proceso(proceso, procs.size()));
        }


    }
}