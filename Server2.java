import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Server2 extends JFrame implements ActionListener {

    JTextArea chatArea;
    JTextField messageField;
    JButton sendButton;

    ServerSocket serverSocket;
    Socket socket;
    BufferedReader input;
    PrintWriter output;

    public Server2() {

        setTitle("TCP Chat Server");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());

        messageField = new JTextField();
        sendButton = new JButton("Send");

        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(this);
        messageField.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);

        startServer();
    }


    public void startServer() {

        try {

            serverSocket = new ServerSocket(5000);

            chatArea.append("Server Started...\n");
            chatArea.append("Waiting for Client...\n");

            socket = serverSocket.accept();

            chatArea.append("Client Connected.\n");


            input = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));


            output = new PrintWriter(
                    socket.getOutputStream(), true);


            Thread receive = new Thread(() -> {

                try {

                    String msg;

                    while ((msg = input.readLine()) != null) {

                        chatArea.append(
                                "Client : " + msg + "\n");
                    }

                } catch (Exception e) {

                    chatArea.append(
                            "Connection Closed.\n");
                }

            });

            receive.start();


        } catch (Exception e) {

            chatArea.append(
                    "Error: " + e.getMessage());
        }
    }


    public void actionPerformed(ActionEvent e) {

        String msg = messageField.getText();

        if (!msg.isEmpty() && output != null) {

            output.println(msg);

            chatArea.append(
                    "Server : " + msg + "\n");

            messageField.setText("");
        }
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Server2();
        });
    }
}
