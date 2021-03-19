package frontsnapk1ck.disinterface.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import frontsnapk1ck.disinterface.util.DIServerUtil;
import frontsnapk1ck.disinterface.util.InterfaceProtocol;
import frontsnapk1ck.disinterface.util.Level;
import frontsnapk1ck.disterface.util.DIUtil;
import frontsnapk1ck.io.FileReader;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DisInterface {

    public static long startupTimeStamp;

    private static JDA jda;
    private static InterfaceProtocol protocol;

    private DisSocket socket;

    

    public DisInterface()
    {
        boolean startedL = false;
        while (!startedL) 
        {
            try 
            {
                start();
                cooldown(5);
                config();
                startedL = true;
            }
            catch (LoginException e) 
            {
                e.printStackTrace();
                cooldown(5);
            }
        }
    }

    private void config() 
    {
        DisInterface.startupTimeStamp = System.currentTimeMillis();
        DisInterface.protocol = new InterfaceProtocol(getJda());
        try 
        {
            this.socket = new DisSocket(DIUtil.PORT, 1, DIUtil.ADDRESS);
            this.socket.listen();
        }
        catch (IOException e)
        {
            System.out.println("Exception caught when trying to listen on port " + DIUtil.PORT + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }  

    private void start() throws LoginException 
    {
        String key = loadKey();
        jda = JDABuilder.createDefault(key).enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL).setChunkingFilter(ChunkingFilter.ALL).build();
    }

    private String loadKey() 
    {
        String[] keyA;
        if ( FileReader.exists(DIServerUtil.KEY_FILE) )
            keyA = FileReader.read(DIServerUtil.KEY_FILE);
        else if ( FileReader.exists(DIServerUtil.KEY_FILE_PI));
            keyA = FileReader.read(DIServerUtil.KEY_FILE_PI);
        return keyA[0];
    }

    public static long getStartupTimeStamp() {
        return startupTimeStamp;
    }

    private void cooldown(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }

        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static JDA getJda() 
    {
        return jda;
    }

    public static InterfaceProtocol getProtocol() 
    {
        return protocol;
    }

    public DisSocket getSocket() 
    {
        return socket;
    }

    public void update() 
    {
        configDiscordInterface();
    }

    private void configDiscordInterface() 
    {
        final long ALLOY_ID = 771814337420460072L;

        final long ERROR_ID = 805626387100467210L;
        final long DEBUG_ID = 809178603345805352L;
        final long WARN_ID  = 809178564452417548L;
        final long INFO_ID  = 809178584635277332L;

        Guild g = jda.getGuildById(ALLOY_ID);

        Map<Level , TextChannel> logs = new HashMap<Level,TextChannel>();
        logs.put(Level.ERROR , g.getTextChannelById( ERROR_ID ));
        logs.put(Level.DEBUG , g.getTextChannelById( DEBUG_ID ));
        logs.put(Level.WARN  , g.getTextChannelById( WARN_ID  ));
        logs.put(Level.INFO  , g.getTextChannelById( INFO_ID  ));

    }

    public void finishInit() 
    {
        this.update();
	}

}