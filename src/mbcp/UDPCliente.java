/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
public class UDPCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try{
            DatagramSocket aSocket = new DatagramSocket();
            String cad= "Hola";
            //modificacion para mandar objeto
            //se crea un nuevo mensaje
            Mensaje me = new Mensaje(1,"Hola",1);
            //vector para obtener los bytes de la clase
            byte[] incoming= new byte[1024];
            // se ocupa los 2 elementos para poder mandar la clase
            ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputstream);
            os.writeObject(me);
            //se parsea el objeto a bytes para mandarlo
            byte[] data = outputstream.toByteArray();
            
            //byte [] m = cad.getBytes();
            InetAddress aHost= InetAddress.getByName("localhost");
            int serverPort = 6789;
            //envio del objecto
            DatagramPacket sendPacket = new DatagramPacket(data, data.length,aHost, serverPort);
            
            //DatagramPacket request = new DatagramPacket(m,cad.length(),
            //        aHost,serverPort);
            aSocket.send(sendPacket);
//            byte [] buffer = new byte[1000];
//            DatagramPacket reply = new DatagramPacket(buffer, 
//                    buffer.length);
            
            //Modificacion recepcion del objeto
            DatagramPacket incomingPacket = new DatagramPacket(incoming, incoming.length);
            
            aSocket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());
            System.out.println("Reply: "+new String(response));
            aSocket.close();
        }catch(SocketException e){System.out.println("Socket: "+e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(UDPCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
