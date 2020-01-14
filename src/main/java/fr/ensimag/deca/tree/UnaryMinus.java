package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * @author gl53
 * @date 01/01/2020
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
              Type unary = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
              if (!unary.isInt() & !unary.isFloat())
              {
                  throw new ContextualError("expression is not an int nor a float",
                                             this.getLocation());
              }
              this.setType(unary);
              return unary;
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

}
