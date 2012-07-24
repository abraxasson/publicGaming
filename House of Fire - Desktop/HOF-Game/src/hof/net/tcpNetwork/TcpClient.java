package hof.net.tcpNetwork;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * TcpClient.java
 *
 * This class works in conjunction with TcpServer.java and TcpPayload.java
  *
 * This client test class connects to server class TcpServer, and in response,
* it receives a serialized an instance of TcpPayload.
 */

public class TcpClient extends Thread
{
    public final static String SERVER_HOSTNAME = "localhost";
    public final static int COMM_PORT = 5050;  // socket port for client comms

    private Socket socket;
    private TcpPayload payload;

    /** Default constructor. */
    public TcpClient()
    {
       
    }
    
    public void run(){
    	 try
         {
             this.socket = new Socket(SERVER_HOSTNAME, COMM_PORT);
             InputStream iStream = this.socket.getInputStream();
             ObjectInputStream oiStream = new ObjectInputStream(iStream);
             this.payload = (TcpPayload) oiStream.readObject();
         }
         catch (UnknownHostException uhe)
         {
             System.out.println("Don't know about host: " + SERVER_HOSTNAME);
             System.exit(1);
         }
         catch (IOException ioe)
         {
             System.out.println("Couldn't get I/O for the connection to: " +
                 SERVER_HOSTNAME + ":" + COMM_PORT);
             System.exit(1);
         }
         catch(ClassNotFoundException cne)
         {
             System.out.println("Wanted class TcpPayload, but got class " + cne);
         }
         System.out.println("Received payload:");
         System.out.println(this.payload.toString());
    }


}
