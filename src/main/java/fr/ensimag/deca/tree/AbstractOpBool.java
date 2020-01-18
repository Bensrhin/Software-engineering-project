package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.codegen.RegisterManager;
/**
 *
 * @author gl53
 * @date 01/01/2020
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
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
        if (t1.isBoolean() & t2.isBoolean())
        {
            return type;
        }
        else 
        {
            throw new ContextualError("Opération booléenne: (" +
                t1.toString() + " " + getOperatorName() +  " " + t2.toString() + 
                ") : non autorisée (règle 3.33)"
                , this.getLocation());
        }

    }
   @Override
<<<<<<< HEAD
    public void codeGenOp(DecacCompiler compiler, GPRegister r1, GPRegister r2){
        //throw new UnsupportedOperationException("not yet implemented");
=======
    public void codeGenOp(DecacCompiler compiler){
        throw new UnsupportedOperationException("not yet implemented");
>>>>>>> 8022a5a1df67cd404e315bd600c119af4c0cad64
    }
    
    @Override
    protected GPRegister codeGenLoad(DecacCompiler compiler) {
        GPRegister r1 = RegisterManager.allocReg(compiler);
        this.codeGenOp(compiler);
        compiler.addInstruction(new LOAD(Register.R1, r1));
        return r1;
    }
}
