package frontsnapk1ck.disterface;

import java.io.IOException;
import java.io.ObjectOutputStream;

import frontsnapk1ck.disterface.util.DIUtil;

public class DisInterClient {

    private DisClient socket;

    public DisInterClient(String name) throws IOException 
    {
        tellConnecting();
        this.socket = new DisClient(DIUtil.ADDRESS, DIUtil.PORT , name );
        sendName();
        tellConnected();
    }

    protected void tellConnected() 
    {
        System.out.println("client connected to " + DIUtil.ADDRESS.getHostAddress() + " on port " + DIUtil.PORT);
    }

    protected void tellConnecting() 
    {
        System.out.println("client is connecting to " + DIUtil.ADDRESS.getHostAddress() + " on port " + DIUtil.PORT);
    }

    public void send(MessageData data) throws IOException 
    {
        send((Object)data);
    }

    protected void sendName() throws IOException 
    {
        send(socket.getName());
    }

    private void send(Object obj) throws IOException 
    {
       final ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
       oos.writeObject(obj);
    }
    
}