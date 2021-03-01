package disinterface.main;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import disterface.MessageData;

public class SendThread extends Thread {

    private Socket client;
    private boolean running;

    public SendThread(Socket client) throws IOException 
    {
        this.client = client;
        config();
	}

    @Override
    public void run() 
    {
        this.running = true;
        try 
        {
            operate(this.client);
        }
        catch (IOException e) 
        {
            if (e.getMessage().equalsIgnoreCase("Connection reset"))
                return;
            e.printStackTrace();
        }
    }

    private void operate(Socket client) throws IOException 
    {
        while (running)
        {
            final BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
            Object obj = readBytes(bis);
            if (obj instanceof MessageData)
                send((MessageData) obj); 
        }
    }

    private Object readBytes(BufferedInputStream bis) 
    {
        try 
        {
            ObjectInputStream in = new ObjectInputStream(bis);
            return in.readObject();
        }
        catch (IOException | ClassNotFoundException e) 
        {
            if (e instanceof SocketException)
            {
                this.running = false;
                final SocketException exception = (SocketException)e;
                String out = this.client.getInetAddress().getHostAddress() + " " +exception.getLocalizedMessage();
                System.out.println(out);
            }
            else
                e.printStackTrace();
            return null;
        }
    }

    private void send(MessageData data) 
    {
        System.out.println(client.getInetAddress().getHostAddress() + " sent a message to " + data.getDestination() );
        DisInterface.getProtocol().processInput(data);
    }

    private void config() 
    {
        this.setDaemon(true);
        String name = "connection from " + client.getInetAddress().getHostName() + " | " +  client.getInetAddress().getHostAddress();

        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);

        this.setName(name);
        this.makeShutdown();
    }

    private void makeShutdown() 
    {
        String name = "shutdown hook " + client.getInetAddress().getHostName() + " | " +  client.getInetAddress().getHostAddress();
        Runtime.getRuntime().addShutdownHook(
            new Thread(
                new Runnable()
                {
                    @Override
                    public void run() 
                    {
                        String clientAddress = client.getInetAddress().getHostAddress();
                        System.err.println(clientAddress + " disconnected");
                    }
                }
                , name)
            );
    }

    public void stopRunning()
    {
        this.running = false;
    }

}
