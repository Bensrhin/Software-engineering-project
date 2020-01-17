/**
 * 
 * @author gl53
 * @date 01/01/2020
 */

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import java.util.*;
public class RegisterManager{
    private LinkedList<Register> regDispo = new LinkedList<Register>();
    private LinkedList<Register> regNonDispo = new LinkedList<Register>();
    public GPRegister allocReg(){
        int cpt = Register.getCpt();
        if(cpt < 16){
            regNonDispo.add(Register.getR(cpt));
            return Register.getR(cpt);
            
        }
        else{
            
        }
        return null;
    }


}