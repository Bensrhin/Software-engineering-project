package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;


/**
 * @author gl53
 * @date 01/01/2020
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }
    @Override
    public void codeGenOp(DecacCompiler compiler, GPRegister r1, GPRegister r2){
        //throw new UnsupportedOperationException("not yet implemented");
        GPRegister R1 = Register.R1;
        this.getLeftOperand().codeGenLoad(compiler, r1);
        this.getRightOperand().codeGenLoad(compiler, r2);
        compiler.addInstruction(new SUB(r2, r1));
        r2.freeR();
        compiler.addInstruction(new LOAD(r1, R1));
        r1.freeR();
    }
    @Override
    public void codeGenPrint(DecacCompiler compiler){
        super.codeGenPrint(compiler);
        if(this.getType().toString().equals("int")){
            compiler.addInstruction(new WINT());
        }
        else{
            compiler.addInstruction(new WFLOAT());
        }
    }
    @Override
    protected void codeGenLoad(DecacCompiler compiler, GPRegister r1) {
        GPRegister r2 = Register.getR(Register.getCpt());
        this.getLeftOperand().codeGenLoad(compiler, r1);
        this.getRightOperand().codeGenLoad(compiler, r2);
        compiler.addInstruction(new SUB(r2, r1));
        r2.freeR();
    }
    
}
