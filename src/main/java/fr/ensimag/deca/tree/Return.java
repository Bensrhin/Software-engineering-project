/*
package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;

import org.apache.commons.lang.Validate;
/**
 * 
 * @author gl53
 * @date 01/01/2020
 */
/*public class Return extends AbstractInst {

    private final AbstractExpr rvalue; 
    
    public AbstractExpr getRvalue()
    {
        return this.rvalue;
    }

    
    public Return(AbstractExpr rvalue) {
        Validate.notNull(rvalue);
        this.rvalue = rvalue;

    }
    public void setRvalue(AbstractExpr rvalue){
        this.rvalue = rvalue;
    }
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        if (returnType.isVoid())
        {
            throw new ContextualError("return must be defferent than void", this.getLocation());
        }
        this.setType(this.getRvalue().verifyExpr(compiler, localEnv, currentClass, returnType));
        

    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        this.getRvalue().decompile(s);
        s.println(";");
        
        
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        rvalue.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        rvalue.prettyPrint(s, prefix, false);

    }
}

    */