/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Mensaje;

/**
 *
 * @author ark
 */
public class UDPServer implements Runnable
{
    private Mensaje recibido;
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public UDPServer()
    {

    }

    public Mensaje getRecibido() { return this.recibido; }

    // public boolean isMessageChanged() { return this.changed; }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        this.changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    @Override
    public void run()
    {
        int vectorProceso =0;
        try{
            // se crea el datagrama para recibir el mensaje
            DatagramSocket aSocket = new DatagramSocket(6789);
            //vector del objeto entrante
            byte [] incoming = new byte[1024];
            while(true)
            {
                // se recibe el  datagrama del objeto
                DatagramPacket incomingPackte = new DatagramPacket(incoming, incoming.length);
                aSocket.receive(incomingPackte);
                // DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                // aSocket.receive(request);
                // Se convierte el mensaje recibido a bytes
                byte[] data = incomingPackte.getData();
                //objetos para obtener el objeto en el mensaje
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                Mensaje mensaje;
                try {
                    // Se obtiene el objeto del mensaje
                    mensaje = (Mensaje) is.readObject();
                    // Se obtienen valores del mensaje
                    // int procesoNumero = mensaje.getProceso();
                    // int numeroMen = mensaje.getMensaje();
                    // String cadena = mensaje.getCadena();
                    // List<Tupla> historial = mensaje.getHistorial();
                    // Condicion de prueba para mostrar mensaje
                    
                    System.out.println(mensaje.toString());

                    this.recibido = mensaje;
                    // No modificar
                    changes.firePropertyChange("CambiaMensaje", null, this.recibido);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Envío de la confirmación de recepción del mensaje
                InetAddress ipAddress = incomingPackte.getAddress();
                int port = incomingPackte.getPort();
                String reply = "Thank you for the message";
                byte[] replyBytea = reply.getBytes();
                DatagramPacket replyPacket = 
                    new DatagramPacket(replyBytea, replyBytea.length, ipAddress, port);
                aSocket.send(replyPacket);
                
                // DatagramPacket reply = new DatagramPacket(request.getData(),
                //         request.getLength(),request.getAddress(),request.getPort());
                // aSocket.send(reply);
            }
        } catch(SocketException e) { System.out.println("Socekt: "+e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(UDPServer.class.getName()).
            log(Level.SEVERE, null, ex);
        }
    }
}
