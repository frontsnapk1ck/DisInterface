package frontsnapk1ck.disterface;

import java.io.Serializable;

public class DisClientName implements Serializable {

    private String name;

    public DisClientName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    
}
