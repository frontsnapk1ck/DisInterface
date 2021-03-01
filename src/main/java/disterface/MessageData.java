package disterface;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import disterface.util.template.Template;

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
        DM("DM"),
        INFO("INFO"),
        WARN("WARN"),
        DEBUG("DEBUG"),
        ERROR("ERROR"),;

        private String id;

        private Destination(String name) 
        {
            this.id = name;
        }

        @Override
        public String toString() 
        {
            return id;
        }

    }
    
}
