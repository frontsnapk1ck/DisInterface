package frontsnapk1ck.disinterface.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DisSocket {

    private ServerSocket server;

    public DisSocket(int port, int backlog, InetAddress bindAddr) throws IOException 
    {
        this.server = new ServerSocket(port, backlog, bindAddr);
        DisInterface.LOGGER.info("DisSocket", "server is listening at " + bindAddr.getHostAddress() + " on port " + port);
	}
    
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }

    public void listen()
    {
        while (true)
        {
            try 
            {
                Socket client = this.server.accept();
                Thread sendThread = new SendThread(client);
                sendThread.start();
            }
            catch (IOException e)
            {
                DisInterface.LOGGER.info("DisSocket", "DisSocket.listen() FAILED");
            }
        }
    
    }
}
