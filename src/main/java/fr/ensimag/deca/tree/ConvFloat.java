package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.FloatType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl53
 * @date 01/01/2020
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        //this.setType(this.getOperand().verifyExpr(compiler, localEnv, currentClass););
        setType(new FloatType(compiler.getSymbols().getSymbol("float")));
        return getType();
    }


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }
    @Override
    protected void codeGenLoad(DecacCompiler compiler, GPRegister r1){
        this.getOperand().codeGenLoad(compiler, r1);
        if(this.getOperand().getType().toString().equals("int")){
            compiler.addInstruction(new FLOAT(r1, r1));
        }
    }

}
