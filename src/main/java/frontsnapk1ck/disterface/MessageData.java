package frontsnapk1ck.disterface;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import frontsnapk1ck.disterface.util.template.Template;

public class MessageData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5301518507706197001L;


    private Destination destination;
    private List<Template> messages;

    public MessageData(List<Template> messages , Destination destination) 
    {
        this.destination = destination;
        this.messages = messages;
    }

    public MessageData(Template message , Destination destination) 
    {
        this(Arrays.asList(message) , destination);
    }

    public List<Template> getMessages() 
    {   
        return messages;
    }

    public Destination getDestination() 
    {
        return destination;
    }

    public enum Destination 
    {
        DM,
        INFO,
        WARN,
        DEBUG,
        ERROR,
    }
    
}
