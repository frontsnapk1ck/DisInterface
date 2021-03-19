package frontsnapk1ck.disinterface.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frontsnapk1ck.disterface.MessageData;
import frontsnapk1ck.disterface.MessageData.Destination;
import frontsnapk1ck.disterface.util.template.Template;
import frontsnapk1ck.utility.Util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class InterfaceProtocol {

    private JDA jda;
    private Map<Destination, MessageChannel> channels;

    public InterfaceProtocol(JDA jda) 
    {
        this.jda = jda;
        this.channels = makeChannels();
    }

    private Map<Destination, MessageChannel> makeChannels() 
    {
        final long ALLOY_ID = 771814337420460072L;
        final long DM_ID = 312743142828933130L;

        final long ERROR_ID = 805626387100467210L;
        final long DEBUG_ID = 809178603345805352L;
        final long WARN_ID  = 809178564452417548L;
        final long INFO_ID  = 809178584635277332L;

        Guild g = jda.getGuildById(ALLOY_ID);

        Map<Destination , MessageChannel> logs = new HashMap<Destination,MessageChannel>();
        logs.put(Destination.ERROR , g.getTextChannelById( ERROR_ID ));
        logs.put(Destination.DEBUG , g.getTextChannelById( DEBUG_ID ));
        logs.put(Destination.WARN  , g.getTextChannelById( WARN_ID  ));
        logs.put(Destination.INFO  , g.getTextChannelById( INFO_ID  ));
        
        logs.put(Destination.DM    , jda.getUserById(DM_ID).openPrivateChannel().complete());

        return logs;
    }

    public boolean processInput(MessageData data) 
    {
        boolean out = true;
        try 
        {
            MessageChannel channel = this.channels.get(data.getDestination());
            List<Template> messages = data.getMessages();
            for (Template message : messages)
                send(message , channel);
        } catch (Exception e) 
        {
            e.printStackTrace();
            out = false;
        }

        return out;
    }

    private void send(Template message, MessageChannel channel) 
    {
        List<MessageEmbed> embeds = getEmbeds(message);
        try 
        {
            for (MessageEmbed messageEmbed : embeds) 
                channel.sendMessage(messageEmbed).complete();    
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private List<MessageEmbed> getEmbeds(Template t) 
    {
        List<MessageEmbed> embeds = new ArrayList<MessageEmbed>();
        try {
            embeds.add(t.getEmbed());
        } catch (Exception ex) 
        {
            String[] arr = t.getText().split("\n");
            String title = arr[0];
            String[] newLines = Util.arrRange(arr, 1);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(title);

            String out = "";
            for (int i = 0; i < newLines.length; i++) 
            {
                String tmp = out + newLines[i] + "\n";
                if (tmp.length() > MessageEmbed.TEXT_MAX_LENGTH)
                {
                    eb.setDescription(out);
                    embeds.add(eb.build());
                    out = newLines[i];
                }
                else
                    out = tmp;
            }
            eb.setDescription(out);
            embeds.add(eb.build());
        }
        return embeds;
    }
}
