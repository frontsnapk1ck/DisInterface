package frontsnapk1ck.disinterface.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.UUID;

import frontsnapk1ck.disterface.DisClientName;
import frontsnapk1ck.disterface.MessageData;

public class SendThread extends Thread {

    private Socket client;
    private boolean running;
    private String name;

    public SendThread(Socket client) throws IOException 
    {
        this.client = client;
        this.name = UUID.randomUUID().toString();
        config();
	}

    @Override
    public void run() 
    {
        this.running = true;
        try 
        {
            while (running)
                operate(this.client);
        }
        catch (IOException e) 
        {
            if (e.getMessage().equalsIgnoreCase("Connection reset"))
                return;
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            DisInterface.LOGGER.error("Send Thread", "Object Class Not Found");
            e.printStackTrace();
        }
    }

    private void operate(Socket client) throws IOException, ClassNotFoundException 
    {
        final ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
        Object obj = ois.readObject();
        if (obj instanceof DisClientName)
            setName((DisClientName)obj);
        if (obj instanceof MessageData)
            send((MessageData) obj); 
    }

    private void setName(DisClientName name) 
    {
        String oldName = getAppName();

        this.name = name.getName();
        String ip = client.getInetAddress().getHostAddress();
    
        this.setName(getAppName() + " | " +  ip);
    
        DisInterface.LOGGER.info("SendThread", "The app " + oldName + " has changed its name to " + name.getName());
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
