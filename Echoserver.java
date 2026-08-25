import java.io.*;
import java.net.*;
public class Echoserver {
    public static void main(String[]args){
        try{
            ServerSocket serverSocket=new ServerSocket(5000);
            System.out.println("Echo server started!...");
            System.out.println("Waiting for client...");

            Socket socket=serverSocket.accept();
            System.out.println("client connected");

            BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output =new PrintWriter(socket.getOutputStream(),true);

            String clientMessage=input.readLine();
            System.out.println("recived from client"+clientMessage);

            output.println(clientMessage);
            System.out.println("Message echoed back to client");

            input.close();
            output.close();
            socket.close();
            serverSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
