package disterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import disterface.MessageData.Destination;
import disterface.util.DIUtil;
import disterface.util.template.Template;

public class DisInterClient {

    private Socket socket;
    private Scanner scanner;

    public DisInterClient() throws IOException {
        System.out.println("client is connecting to " + DIUtil.ADDRESS.getHostAddress() + " on port " + DIUtil.PORT);
        this.socket = new Socket(DIUtil.ADDRESS, DIUtil.PORT);
        System.out.println("client connected to " + DIUtil.ADDRESS.getHostAddress() + " on port " + DIUtil.PORT);
        this.scanner = new Scanner(System.in);
        start();
    }

    private void start() throws IOException 
    {
        String input;
        while (true) 
        {
            input = scanner.nextLine();

            Template t = new Template("", input);
            MessageData data = new MessageData( t , Destination.DM);
            byte[] bytes = convertToBytes(data);

            OutputStream out = this.socket.getOutputStream();
            out.write(bytes);
            System.err.println("sent " + bytes.length + " bytes to the server");

            // PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            // out.println(input);
            // out.flush();
        }
    }

    private byte[] convertToBytes(Object object) throws IOException
    {
        try 
        (
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos)
        ) 
        {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }
    
    
}
