
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class client {
    public static void main(String[] args){
        try {
            //creating a socket to connect to the server
            Socket server = new Socket("127.0.0.1",5000);
            //creating input and output
            DataInputStream inStream = new DataInputStream(server.getInputStream());
            DataOutputStream outStream = new DataOutputStream(server.getOutputStream());
            
            while (true)
            {  
                Scanner scan = new Scanner(System.in);
                String ServerMessage = inStream.readUTF();                
                if(ServerMessage.equalsIgnoreCase("Bye"))
                {
                    System.out.println("Session ended");
                    break;
                }
                System.out.println(ServerMessage);
                String UserMessage= scan.nextLine();
                outStream.writeUTF(UserMessage);
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            server.close();      
        } 
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }   
}