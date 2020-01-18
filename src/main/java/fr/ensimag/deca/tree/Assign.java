package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.INT;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl53
 * @date 01/01/2020
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, type));
        Type type2 = this.getRightOperand().getType();
        if (!localEnv.assignCompatible(type, type2))
        {
            throw new ContextualError("The affected type is not compatible with "
                + type.toString(), this.getLocation());
        }
        this.setType(type);
        return getType();
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        GPRegister r1 = this.getRightOperand().codeGenLoad(compiler);
        compiler.addInstruction(new STORE(r1, this.getLeftOperand().getExpDefinition().getOperand()));
        
        
}

}
