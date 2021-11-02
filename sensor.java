

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class sensor{
    public static void main(String[] args){
        try{
            ServerSocket sensorServer = new ServerSocket(2000);
            System.out.println("Sensor is now Running...");

            Socket serversocket = sensorServer.accept();
            DataInputStream serverIn = new DataInputStream(serversocket.getInputStream());
            DataOutputStream serverOut= new DataOutputStream(serversocket.getOutputStream());
            System.out.println("Sensor is Connected");
            while(true){
               String location = serverIn.readUTF();
               System.out.println("Client submitted a request");
               System.out.println("[Client]:Destination is "+ location);

               String carType = serverIn.readUTF();
               System.out.println("[Client]: The Client is Driving a "+carType);

               if(carType.equalsIgnoreCase("bike")){
                serverOut.writeUTF("[App]:The best route is to take the next exit then turn right and continue straight for 200m ");
               }
               else if(carType.equalsIgnoreCase("car")){
                serverOut.writeUTF("[App]:The best route is to continue straight for 2.8 km and take the bridge and take the 2nd right ");
               }
               else if(carType.equalsIgnoreCase("Bus")){
                serverOut.writeUTF("[App]:The best route is to turn right and then take the exit and then take the first right before the bridge");
               }
			   else if(carType.equalsIgnoreCase("Walking")){
                serverOut.writeUTF("[App]:The best route is to turn right and then continue straightfor 2kms and take the exit and then take the first right before the bridge");
               }
               
            }
        }
         catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}