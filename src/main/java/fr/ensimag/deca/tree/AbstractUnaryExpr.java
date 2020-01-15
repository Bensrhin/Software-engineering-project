package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
/**
 * Unary expression.
 *
 * @author gl53
 * @date 01/01/2020
 */
public abstract class AbstractUnaryExpr extends AbstractExpr {

    public AbstractExpr getOperand() {
        return operand;
    }
    private AbstractExpr operand;
    public AbstractUnaryExpr(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }


    protected abstract String getOperatorName();
  
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        String op = getOperatorName();
        //if (op.equals("!")||op.equals("-"))
        //{
        s.print(" " + op + " ");
        //}
        getOperand().decompile(s);
        s.print(")");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        operand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }
    @Override
    protected void codeGenLoad(DecacCompiler compiler, GPRegister r1) {
        throw new UnsupportedOperationException("not yet implementedoki");
    }
    @Override
    public void codeGenPrint(DecacCompiler compiler){
        GPRegister r1 = Register.getR(Register.getCpt());
        this.codeGenOp(compiler, r1);
        if(this.getType().toString().equals("int")){
            compiler.addInstruction(new WINT());
        }
        else if(this.getType().toString().equals("float")){
            compiler.addInstruction(new WFLOAT());
        }
    }
    public void codeGenOp(DecacCompiler compiler, GPRegister r1){
        throw new UnsupportedOperationException("not yet implemented");
    }


}
