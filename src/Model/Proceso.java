package Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import UDP.UDPCliente;
import UDP.UDPServer;

/**
 * Proceso
 * 
 * @author BaezCrdrm
 */
public class Proceso implements PropertyChangeListener {
    private String address;
    private int id, puerto;
    private List<Mensaje> buffer, recibidos;
    private List<Tupla> ci;
    private int[] vt;
    private UDPServer udps;
    private Thread server;

    /**
     * Crea la definición de un proceso
     * 
     * @param ip     Dirección IP del equipo que ejecutará el proceso
     * @param puerto Puerto del equipo que ejecutará el proceso
     * @param id     Identificador del proceso
     */
    public Proceso(String ip, int puerto, int id) {
        this.address = ip;
        this.puerto = puerto;
        this.id = id;
    }

    /**
     * Crea un nuevo proceso que será utilizado en el algoritmo
     * 
     * @param proceso Proceso de definición del cual se obtendrán los datos del
     *                proceso
     * @param n       Número de procesos. Creará el vector de tamaño 'n'
     */
    public Proceso(Proceso proceso, int n) {
        try {
            this.id = proceso.getId();
            this.address = proceso.getAddress();
            this.puerto = proceso.getPuerto();

            this.buffer = new ArrayList<Mensaje>();
            this.recibidos = new ArrayList<Mensaje>();
            this.vt = new int[n];
            this.ci = new ArrayList<Tupla>();
            this.udps = new UDPServer(this.puerto);
            this.udps.addPropertyChangeListener(this);
            serve();
        } catch (NullPointerException npe) {
            System.out.println("Ocurró un error al crear el proceso");
            System.out.println("Agrega los valores correctos al parámetro");
            System.out.println(npe.getMessage());
            npe.printStackTrace();
            // throw new NullPointerException();
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
    public int getPuerto() {
        return puerto;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public List<Tupla> getCI() {
        return this.ci;
    }

    public List<Mensaje> getOrden() {
        return this.recibidos;
    }

    public boolean esIniciable() {
        if (!((this.address == null || this.address.equals(""))))
            return true;
        else
            return false;
    }

    /**
     * Actualiza el reloj lógico del proceso.
     * 
     * @param tupla Tupla que contiene los valores del mensaje
     */
    public void actualizaVector(Tupla tupla) {
        this.vt[tupla.getProceso()] = tupla.getMensaje();
    }

    /**
     * Actualiza el reloj lógico del proceso.
     * 
     * @param proceso ID del proceso
     * @param mensaje Número de mensaje
     */
    public void actualizaVector(int proceso, int mensaje) {
        this.vt[proceso] = mensaje;
    }

    /**
     * Actualiza el reloj lógico del proceso.
     * 
     * @param proceso ID del proceso
     * 
     */
    public void actualizaVector(int proceso) {
        this.vt[proceso] += 1;
    }

    public boolean comparaSiguienteValor(int msg) {
        return (msg == this.vt[id] + 1) ? true : false;
    }

    public boolean comparaEnHistorial(List<Tupla> historial) {
        for (Tupla tupla : historial) {
            if (!(tupla.getMensaje() <= this.vt[tupla.getProceso()]))
                return false;
        }
        return true;
    }

    public void delivery(Mensaje msg) {
        this.vt[id] += 1;
        if (msg.coincideCon(this.ci))
            poda();
     
        this.ci.add(msg.getTupla());
        poda(msg.getHistorial());
        this.recibidos.add(msg);

        if(this.buffer.contains(msg))
            this.buffer.remove(msg);
        
    }

    /**
     * Remueve Tuplas de CI del proceso con base en el historial dado por el mensaje
     */
    private void poda() {
        this.ci.remove(ci.stream().findAny().filter(p -> p.getProceso() == this.id).get());
    }

    /**
     * Remueve Tuplas de CI del proceso con base en el historial dado por el mensaje
     * 
     * @param historial
     */
    private void poda(List<Tupla> historial) {
        for (Tupla tci : this.ci) {
            for (Tupla his : historial) {
                if (tci.getProceso() == his.getProceso())
                    this.ci.remove(tci);
            }
        }
    }

    /**
     * Verifica si existen procesos en espera y evalúa si es que pueden ser
     * entregados
     */
    private void verificarMensajesEnEspera() {
        for (Mensaje msg : this.buffer) {
            ordenar(msg);
        }
    }

    /**
     * Método principal que permite el ordenamiento
     * 
     * @param msg
     */
    private void ordenar(Mensaje msg) {
        if (!(comparaSiguienteValor(msg.getMensaje()) && comparaEnHistorial(msg.getHistorial()))) {
            // Wait
            buffer.add(msg);
        } else {
            delivery(msg);

            if (!this.buffer.isEmpty())
                verificarMensajesEnEspera();
        }
    }

    public void clearCI() {
        this.ci.clear();
    }

    /**
     * Enviar un mensaje a un proceso dado
     * 
     * @param msg          Mensaje que se envía
     * @param destinatario Proceso al que se le envía el mensaje
     */
    public void enviarMensaje(Mensaje msg, Proceso destinatario) {
        msg.setHistorial(destinatario.getCI());
        destinatario.clearCI();

        try {
            UDPCliente.enviarUDP(msg, destinatario);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al intentar enviar el mensaje " + msg.toString());
            System.out.print(e.getMessage() + "\n");
        }
    }

    public void serve()
    {
        this.server = new Thread(this.udps);
        this.server.start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        // System.out.println(evt.getPropertyName());
        ordenar(this.udps.getRecibido());
    }

    public static String getMachineIp() {
        String ip = "";
        try (final java.net.DatagramSocket socket = new java.net.DatagramSocket()) {
            socket.connect(java.net.InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (Exception ex) {
        }

        return ip;
    }
}