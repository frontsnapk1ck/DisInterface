package frontsnapk1ck.disterface.util.template;

import java.io.Serializable;

import net.dv8tion.jda.api.entities.MessageEmbed.Field;

public class DisField implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 134254323324324L;

    private String name;
    private String value;
    private boolean isInline;

	public DisField(Field f) 
    {
        this.isInline = f.isInline();
        this.value = f.getValue();
        this.name = f.getName();
	}

	public Field build() 
    {
        return new Field( name, value , isInline);
	}
    
}
