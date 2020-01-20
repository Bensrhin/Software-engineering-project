package fr.ensimag.deca.tree;

/**
 * Visibility of a field.
 *
 * @author gl53
 * @date 01/01/2020
 */
public class Visibility
{   
    public enum Vis 
    {
    PUBLIC,
    PROTECTED
    }
    private String visibility;
    public Visibility()
    {
        this.visibility = new String("public");
    }
    public Visibility(String type)
    {
        this.visibility = type;
    }
    public String getValue() {
        return visibility;
    }
    String prettyPrintNode() {
        return getValue();
    }
    
}

