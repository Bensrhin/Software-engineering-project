package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;

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
        throw new UnsupportedOperationException("not yet implemented");
    }


}
