package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * @author gl53
 * @date 01/01/2020
 */
public class Initialization extends AbstractInitialization {

    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type2 = this.getExpression().verifyExpr(compiler, localEnv, currentClass);

        if (!localEnv.assignCompatible(t, type2))
        {
            throw new ContextualError("Initialisation d'une variable de type " +
                t.toString() + " par une valeur de type " + type2.toString() +
                " : non autorisée (règle 3.8)"
                , this.getExpression().getLocation());
        }

        if (t.sameType(type2))
        {
            this.expression.setType(type2);
        }
        else
        {
            if (type2.isInt())
            {
              this.expression = new ConvFloat(this.expression);
              this.expression.verifyExpr(compiler, localEnv,currentClass);
              this.expression.setType(t);
            }
            else if (type2.isClass())
            {
              /* todo */
            }

        }

    }

    @Override
    protected void codeGenInt(DecacCompiler compiler, int i){
        //throw new UnsupportedOperationException("not yet implemented");
        //GPRegister r = Register.getR(Register.getCpt());
        GPRegister r = this.expression.codeGenLoad(compiler);
       // System.out.println(this.getType());
        compiler.addInstruction(new STORE(r,new RegisterOffset(i, Register.GB)));
        r.freeR();

    }
    @Override
    public void decompile(IndentPrintStream s) {
      s.print(" = ");
      this.getExpression().decompile(s);
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }
}
