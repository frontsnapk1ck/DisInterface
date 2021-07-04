package frontsnapk1ck.disterface.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import frontsnapk1ck.disterface.util.constants.DIConstants;

public class DIUtil implements DIConstants {

	public static InetAddress ADDRESS;

    static {
        try 
        {
            ADDRESS = InetAddress.getByName("192.168.15.1");
        }
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        }
    }
    
}
