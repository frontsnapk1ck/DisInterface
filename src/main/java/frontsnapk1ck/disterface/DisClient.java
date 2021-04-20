package frontsnapk1ck.disterface;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class DisClient extends Socket {

    private DisClientName name;

    public DisClient(InetAddress address, int port , String name) throws IOException
    {
        super(address,port);
        this.name = new DisClientName(name);
    }

    public DisClientName getName() 
    {
        return name;
    }


    
}
