import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DNSClient {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("192.168.1.18"); // Localhost
            int serverPort = 12345;

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("Connected to DNS Client.");
            System.out.print("Enter domain name to lookup (e.g., www.google.com): ");
            String domainName = reader.readLine();

            // Send request to server
            byte[] sendData = domainName.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);

            // Receive response from server
            byte[] receiveBuffer = new byte[512];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);

            // Parse and display response
            String ipAddress = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Response from DNS Server -> IP Address: " + ipAddress);

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
