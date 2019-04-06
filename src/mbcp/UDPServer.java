/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbcp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ark
 */
public class UDPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int vectorProceso =0;
        try{
            // se crea el datagrama para recibir el mensaje
            DatagramSocket aSocket = new DatagramSocket(6789);
            //vector del objeto entrante
            byte [] incoming = new byte[1024];
            while(true){
                // se recibe el  datagrama del objeto
                DatagramPacket incomingPackte = new DatagramPacket(incoming, incoming.length);
                aSocket.receive(incomingPackte);
//                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
//                aSocket.receive(request);
                //se combierte el mensaje recibido a bytes
                byte[] data = incomingPackte.getData();
                //objetos para obtener el objeto en el mensaje
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                Mensaje mensaje;
                try {
                    //se obtiene el objeto del mensaje
                    mensaje = (Mensaje) is.readObject();
                    //se obytienen valores del mensaje
                    int numero = mensaje.getNumero();
                    String cadena = mensaje.getCad();
                    int vector = mensaje.getVector();
                    //condicion de prueba para mostrar mensaje
                    if(vector==vectorProceso+1){
                        System.out.println("mensaje recibido: "+ numero+", "+cadena+", "+vector);
                        vectorProceso++;
                    }else{
                        System.out.println("mensaje almacenado");
                    }
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                //envio de la confirmacion de recepcion del mensaje
                InetAddress ipAddress = incomingPackte.getAddress();
                int port = incomingPackte.getPort();
                String reply = "Thank you for the message";
                byte[] replyBytea = reply.getBytes();
                DatagramPacket replyPacket
                        = new DatagramPacket(replyBytea, replyBytea.length, ipAddress, port);
                aSocket.send(replyPacket);
                
//                DatagramPacket reply = new DatagramPacket(request.getData(),
//                        request.getLength(),request.getAddress(),request.getPort());
//                aSocket.send(reply);
            }
            }catch(SocketException e){System.out.println("Socekt: "+e.getMessage());
            }catch(IOException e){System.out.println("IO: "+e.getMessage());
        }
    }
    
}
