package frontsnapk1ck.disinterface.util;

import java.io.Serializable;

import net.dv8tion.jda.api.entities.Message;

public class MessageContainer implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -2983205757321699313L;
    private Message message;
    

    public MessageContainer(Message message) 
    {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
