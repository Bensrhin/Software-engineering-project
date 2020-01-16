package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.SUB;
/**
 *
 * @author gl53
 * @date 01/01/2020
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type t2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        this.setType(this.typeBool(compiler, t1, t2));
        return getType();
    }
    public Type typeBool(DecacCompiler compiler, Type t1, Type t2) throws ContextualError
    {
        String op = this.getOperatorName();
        Type type = new BooleanType(compiler.getSymbols().getSymbol("boolean"));
        if ( ( (t1.isBoolean() & t2.isBoolean())
                ||(t1.isClassOrNull() & t2.isClassOrNull()))
            &(op.equals("==")||op.equals("!=")))
        {
            return type;
        }
        /* comparaison class todo */

        /***/
        else if ((op.equals("==")||op.equals("!=")||op.equals("<")
            ||op.equals("<=")||op.equals(">")||op.equals(">="))
            & t1.isTypeBinary() & t2.isTypeBinary())
        {
            return type;
        }
        /*if ((op.equals("==")||op.equals("!="))
             &t1.isClassOrNull()&t2.isClassOrNull())
        {
            return type;
        }
        */
        else
        {
            throw new ContextualError("on autorise pas "
                + "la comparaison dans ce cas", this.getLocation());
        }
    }
    @Override
    public void codeGenOp(DecacCompiler compiler, GPRegister r1, GPRegister r2){
        GPRegister R1 = Register.R1;
        this.getLeftOperand().codeGenLoad(compiler, r1);
        this.getRightOperand().codeGenLoad(compiler, r2);
        compiler.addInstruction(new SUB(r2, r1));
        compiler.addInstruction(new LOAD(r1, R1));
        Label opIf = new Label("OpCmp_if_in_"+this.getLeftOperand()
                .getLocation().toStringLabel());
        Label opFin = new Label("OpCmp_fin_in_"+this.getLeftOperand()
                .getLocation().toStringLabel());
        // appel instruction de la fille
        this.codeGenIma(compiler, opIf);
        compiler.addInstruction(new LOAD(1, R1));
        compiler.addInstruction(new BRA(opFin));
        compiler.addLabel(opIf);
        compiler.addInstruction(new LOAD(0, R1));
        compiler.addLabel(opFin);
    };

    public abstract void codeGenIma(DecacCompiler compiler, Label label);
    
    @Override
    protected void codeGenLoad(DecacCompiler compiler, GPRegister r1) {
        GPRegister r2 = Register.getR(Register.getCpt());
        this.codeGenOp(compiler, r1, r2);
        compiler.addInstruction(new LOAD(Register.R1, r1));
        r2.freeR();
    }


}
