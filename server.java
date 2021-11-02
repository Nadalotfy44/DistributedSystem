
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ClinetHandler extends Thread{
    Socket client;
    DataInputStream SensorInput;
    DataOutputStream SensorOutput;

    public ClinetHandler(Socket client ,DataInputStream SensorInput,DataOutputStream SensorOutput){
        this.client = client;
        this.SensorInput = SensorInput;
        this.SensorOutput=  SensorOutput;
    }

    @Override
    public void run() {
        try{
            DataInputStream clientInput = new DataInputStream(client.getInputStream());
            DataOutputStream clientOutput = new DataOutputStream(client.getOutputStream());
                 while (true)
                {
                    clientOutput.writeUTF("[App]:Welcome to the navigator app\nWhere do you want to go ?");
                    clientOutput.flush();
                    String location =clientInput.readUTF();
                    SensorOutput.writeUTF(location);
                    SensorOutput.flush();
                    System.out.println("Client destination is "+location);

                    clientOutput.writeUTF("[App]:What are you Driving? (Bus,Car,Bike)");
                    clientOutput.flush();
                    String type =clientInput.readUTF();  
                    SensorOutput.writeUTF(type);
                    SensorOutput.flush();

                    String navigation = SensorInput.readUTF();
                    clientOutput.writeUTF(navigation +"\n[App]:Do you still need additonal help (yes/no)");
                    clientOutput.flush();

        
                    String response =clientInput.readUTF();
                  

                    if (response.equalsIgnoreCase("no"))
                    {
                        clientOutput.writeUTF("[App]:Thank you for Using our app, Drive Safely");
                        clientOutput.flush();
                        break;
                    }
                }

                clientInput.close();
                clientOutput.close();
                client.close();
            }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());

        }
 }
}

public class serverThreaded {
    
    public static void main(String[] args)
    {
        try
        {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server Running...");
            Socket SensorServer = new Socket("127.0.0.1",3000);

            DataInputStream SensorInput = new DataInputStream(SensorServer.getInputStream());
            DataOutputStream SensorOutput = new DataOutputStream(SensorServer.getOutputStream());
            while(true){
                Socket newClientsocket = server.accept();
                ClinetHandler newClient = new ClinetHandler(newClientsocket,SensorInput,SensorOutput);
                newClient.start();
            }

            
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    
}
