package disterface.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import disterface.util.constants.DIConstants;

public class DIUtil implements DIConstants {

	public static InetAddress ADDRESS;

    static {
        try 
        {
            ADDRESS = InetAddress.getByName("192.168.1.102");
        }
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        }
    }
    
}
