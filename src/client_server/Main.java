package client_server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main extends JFrame implements Runnable {
    static private Socket connection;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;

    public static void main(String [] args){
      new Thread(new Main("Test")).start();
      new Thread(new Server()).start();
    }
    public Main(String name){
        super(name);
        setLayout(new FlowLayout());
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        final JTextField text = new JTextField(10);
        final JButton b1 = new JButton("Send");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == b1){
                    sendData(text.getText());
                }
            }
        });
        add(text);
        add(b1);
    }

    @Override
    public void run(){
        try {

            while(true){
                connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                JOptionPane.showMessageDialog(null, (String)input.readObject());
            }
        }catch (UnknownHostException e){
        }catch (IOException e){
        }catch (HeadlessException e){
        }catch (ClassNotFoundException e){}
    }

    private static void  sendData(Object obj){
        try{
            output.flush();
            output.writeObject(obj);
        }catch (IOException e){}
    }
}
