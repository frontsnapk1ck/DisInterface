package frontsnapk1ck.disterface.util.template;

import java.io.Serializable;


public class Template implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2510318876797726797L;

    
    private String message;
    private String title;


    public Template(String title, String s) 
    {
        this.title = title;
        this.message = s;
    }

    public String getText()
    {
        String out = title;
        out += "\n";
        out += message;
        return out;
    }
}