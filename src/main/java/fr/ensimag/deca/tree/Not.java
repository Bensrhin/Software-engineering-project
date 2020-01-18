package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl53
 * @date 01/01/2020
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
              Type unary = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
              if (!unary.isBoolean())
              {
                  throw new ContextualError("L'opération unaire: [" +
                " ! ( " + unary.toString() + 
                " ) ] n'est pas autorisée (règle 3.33)", this.getLocation());
              }
              this.setType(unary);
              return unary;
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }
}
