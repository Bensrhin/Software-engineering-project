package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.WINT;


/**
 * @author gl53
 * @date 01/01/2020
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }
    @Override
    public void codeGenOp(DecacCompiler compiler, GPRegister r1, GPRegister r2){
        //throw new UnsupportedOperationException("not yet implemented");
        GPRegister R1 = Register.R1;
        this.getLeftOperand().codeGenLoad(compiler, r1);
        this.getRightOperand().codeGenLoad(compiler, r2);
        compiler.addInstruction(new ADD(r2, r1));
        r2.freeR();
        compiler.addInstruction(new LOAD(r1, R1));
        r1.freeR();
    }
    @Override
    public void codeGenPrint(DecacCompiler compiler){
        super.codeGenPrint(compiler);
        compiler.addInstruction(new WINT());
    }
    @Override
    protected void codeGenLoad(DecacCompiler compiler, GPRegister r1) {
        GPRegister r2 = Register.getR(Register.getCpt());
        this.getLeftOperand().codeGenLoad(compiler, r1);
        this.getRightOperand().codeGenLoad(compiler, r2);
        compiler.addInstruction(new ADD(r2, r1));
        r2.freeR();
    }
}
