package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.INT;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.QUO;


/**
 *
 * @author gl53
 * @date 01/01/2020
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }
@Override
    public void codeGenOp(DecacCompiler compiler, GPRegister r1, GPRegister r2){
        //throw new UnsupportedOperationException("not yet implemented");
        GPRegister R1 = Register.R1;
        this.getLeftOperand().codeGenLoad(compiler, r1);
        this.getRightOperand().codeGenLoad(compiler, r2);
        if(this.getLeftOperand().getType().toString().equals("int") && this.getRightOperand().getType().toString().equals("int")){
            compiler.addInstruction(new QUO(r2, r1));
        }
        else{
            compiler.addInstruction(new DIV(r2, r1));
        }
        r2.freeR();
        compiler.addInstruction(new LOAD(r1, R1));
        r1.freeR();
    }
    @Override
    protected void codeGenLoad(DecacCompiler compiler, GPRegister r1) {
        GPRegister r2 = Register.getR(Register.getCpt());
        this.getLeftOperand().codeGenLoad(compiler, r1);
        this.getRightOperand().codeGenLoad(compiler, r2);
        if(this.getLeftOperand().getType().toString().equals("int") && this.getRightOperand().getType().toString().equals("int")){
            compiler.addInstruction(new QUO(r2, r1));
        }
        else{
            compiler.addInstruction(new DIV(r2, r1));
        }
        r2.freeR();
    }

}
