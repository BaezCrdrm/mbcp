/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbcp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Mensaje;
import Model.Proceso;

/**
 *
 * @author Samuel
 */
public class MBCP {

    /**
     * @param args the command line arguments
     */
    static List<Proceso> procesos;

    public static void main(String[] args) {
        procesos = new ArrayList<Proceso>();
        procesos.add(new Proceso(Proceso.getMachineIp(), 101, 0));
        procesos.add(new Proceso(Proceso.getMachineIp(), 102, 1));

        // while(true)
        // {
        // System.out.println("1. Ingresa nuevo proceso\n" +
        // "2. Ejecuta");
        // }

        Ordenamiento o = new Ordenamiento(procesos);
        Thread ordenamiento = new Thread(o);
        ordenamiento.start();
        

        Scanner lee = new Scanner(System.in);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        while(true)
        {
            System.out.println("1. Ingresa ID proceso");
            int n = lee.nextInt();

            try {
                List<Mensaje> msgs = o.getOrden(n);
            
                for(Mensaje m : msgs)
                {
                    System.out.println(m.toString());
                }
            } catch (Exception e) {
                
            }
        }
    }
    
}
