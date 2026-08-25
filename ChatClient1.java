import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClient1 extends JFrame implements ActionListener {

    JTextArea chatArea;
    JTextField messageField;
    JButton sendButton;

    Socket socket;
    BufferedReader input;
    PrintWriter output;

    public ChatClient1() {

        setTitle("TCP Chat Client");
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

        setVisible(true);

        connectToServer();
    }


    public void connectToServer() {

        try {

            // Server IP address
            socket = new Socket("192.168.1.74", 5000);

            chatArea.append("Connected to Server.\n");

            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            output = new PrintWriter(
                    socket.getOutputStream(), true);


            Thread receive = new Thread(() -> {

                try {

                    String msg;

                    while ((msg = input.readLine()) != null) {

                        chatArea.append(
                                "Server : " + msg + "\n");
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

        if (!msg.isEmpty()) {

            output.println(msg);

            chatArea.append(
                    "Client : " + msg + "\n");

            messageField.setText("");
        }
    }


    public static void main(String[] args) {

        new ChatClient1();
    }
}
