package frontsnapk1ck.disterface.util.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

public class Template implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2510318876797726797L;

    
    private String message;
    private String title;

    private String footerName;
    private String footerURL;
    private String titleURL;
    private List<DisField> fields;
    private String imageURL;


    public Template(String title, String s) 
    {
        this.title = title;
        this.titleURL = "";
        this.imageURL = "";
        this.message = s;
        this.fields = new ArrayList<DisField>();
    }

    public MessageEmbed getEmbed()
    {
        //make builder
        EmbedBuilder eb = new EmbedBuilder();

        //check to see if they need to add a link to the title
        if (titleURL.equals(""))
            eb.setTitle(title);
        else
            eb.setTitle(title, titleURL);
        
        //check to see if you need to add an image
        if (!this.imageURL.equals( "" ))
            eb.setThumbnail(this.imageURL);
        
        //set descriptions
        eb.setDescription(message);
        eb.setFooter(footerName, footerURL);

        //add all fields in the list
        for (DisField field : fields)
            eb.addField(field.build());
        
        //finally build and return the thing
        return eb.build();
    }

    public String getText()
    {
        String out = title;
        out += "\n";
        out += message;
        return out;
    }

    public String getFooterName()
    {
        return footerName;
    }

    public String getFooterURL()
    {
        return footerURL;
    }

    public void setFooterName(String footerName)
    {
        this.footerName = footerName;
    }

    public void setFooterURL(String footerURL)
    {
        this.footerURL = footerURL;
    }

    public void addFelid(String name, String value, boolean inline) 
    {
        Field f = new Field(name, value, inline);
        this.addFelid(f);
	}

    public void addFelid(Field f) 
    {
        this.fields.add(new DisField(f));
    }

    public void setTitle(String title, String url) 
    {
        this.titleURL = url;
        this.title = title;
	}
    
    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }
}
