package fr.ensimag.ima.pseudocode;

/**
 * Immediate operand representing a string.
 * 
 * @author Ensimag
 * @date 01/01/2020
 */
public class ImmediateString extends Operand {
    private String value;

    public ImmediateString(String value) {
        super();
        //this.value = value;
        this.value = value.substring(1, value.length()-1);//enlever les ""
    }

    @Override
    public String toString() {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
