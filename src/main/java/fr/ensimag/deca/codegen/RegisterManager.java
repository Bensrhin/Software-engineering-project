package fr.ensimag.deca.codegen;
/**
 * 
 * @author gl53
 * @date 01/01/2020
 */

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import java.util.*;
public class RegisterManager{
    private static InitManager initR = new InitManager(3);
    //System.out.println(initR);
    public static GPRegister allocReg(DecacCompiler compiler){
        int cpt = Register.getCpt();
        if(!initR.regDispo.empty()){
            GPRegister r = initR.regDispo.pop();
            initR.regNonDispo.add(r);
            return r;    
        }
        else{
            GPRegister r = initR.getNonDispo();
            compiler.addInstruction(new TSTO(1));
            compiler.addInstruction(new BOV(compiler.pilePleine));
            compiler.addInstruction(new PUSH(r));
            return r;
        }
    }
    public static void freeReg(DecacCompiler compiler, GPRegister r){
        if(initR.regEcrase.contains(r)){ 
            compiler.addInstruction(new POP(r));
            initR.regEcrase.pop();
             compiler.addInstruction(new TSTO(-1));
        }
        else{
            r.freeR();
            initR.regNonDispo.remove(r);
            initR.regDispo.push(r);        
        }
    }


}