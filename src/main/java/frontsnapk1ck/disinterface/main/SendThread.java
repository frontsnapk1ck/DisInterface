package frontsnapk1ck.disinterface.main;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import frontsnapk1ck.disterface.DisClientName;
import frontsnapk1ck.disterface.MessageData;

public class SendThread extends Thread {

    private Socket client;
    private boolean running;
    private String name;

    public SendThread(Socket client) throws IOException 
    {
        this.client = client;
        this.name = "UNNAMED";
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
            if (obj instanceof DisClientName)
                setName((DisClientName)obj);
            if (obj instanceof MessageData)
                send((MessageData) obj); 
        }
    }

    private void setName(DisClientName name) 
    {
        String oldName = getAppName();

        this.name = name.getName();
        String ip = client.getInetAddress().getHostAddress();
    
        this.setName(getAppName() + " | " +  ip);
    
        DisInterface.LOGGER.info("SendThread", "The app " + oldName + " has changed its name to " + name.getName());
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
        String ip = client.getInetAddress().getHostAddress();
        String destination = String.valueOf(data.getDestination());

        DisInterface.LOGGER.debug("SendThread", "The app " + getAppName() + " running on " + ip + " sent a message to " +  destination );

        DisInterface.getProtocol().processInput(data);
    }

    private void config() 
    {
        this.setDaemon(true);

        String ip = client.getInetAddress().getHostAddress();

        String name =  getAppName() + " | " +  ip;

        String clientAddress = client.getInetAddress().getHostAddress();
        DisInterface.LOGGER.info("SendThread", "New connection from app " + getAppName() + " running on " + clientAddress);

        this.setName(name);
        this.makeShutdown();
    }

    private void makeShutdown() 
    {
        String ip = client.getInetAddress().getHostAddress();
        String name =  getAppName() + " | " +  ip;
        
        Runtime.getRuntime().addShutdownHook(
            new Thread(
                new Runnable()
                {
                    @Override
                    public void run() 
                    {
                        String clientAddress = client.getInetAddress().getHostAddress();
                        DisInterface.LOGGER.info("SendThread", "App " + getAppName() + " running on " + clientAddress + " disconnected");
                    }
                }
                , name)
            );
    }

    protected String getAppName()
    {
        return name;
    }

    public void stopRunning()
    {
        this.running = false;
    }

}
