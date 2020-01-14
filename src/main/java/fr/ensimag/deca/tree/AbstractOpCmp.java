package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.IntType;
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
        return this.typeBool(compiler, t1, t2);
    }
    public Type typeBool(DecacCompiler compiler, Type t1, Type t2) throws ContextualError
    {
        String op = this.getOperatorName();
        Type type = new IntType(compiler.getSymbols().getSymbol("int"));
        if (t1.isBoolean() & t2.isBoolean() 
            &(op.equals("==")||op.equals("!=")))
        {
            return type;
        }
        /* comparaison class todo */
        
        /***/
        if ((op.equals("==")||op.equals("!=")||op.equals("<")
            ||op.equals("<=")||op.equals(">")||op.equals(">="))
            & t1.isType() & t2.isType())
        {
            return type;
        }
        if ((op.equals("==")||op.equals("!="))
             &t1.isClassOrNull()&t2.isClassOrNull())
        {
            return type;
        }
        throw new ContextualError("on autorise pas "
                + "la comparaison dans ce cas", this.getLocation());
    }
    @Override
    public abstract void codeGenOp(DecacCompiler compiler, GPRegister r1, GPRegister r2);


}
