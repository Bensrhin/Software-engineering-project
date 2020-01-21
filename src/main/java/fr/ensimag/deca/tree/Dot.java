/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

/**
 *
 * @author 
 */
public class Dot extends AbstractExpr{
    private AbstractExpr expr;
    private AbstractIdentifier id;
    public Dot(AbstractExpr expr, AbstractIdentifier id){
       this.id = id;
       this.expr = expr; 
    }
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        return null;
    }
    @Override
    protected void iterChildren(TreeFunction f) {
        id.iterChildren(f);
        expr.iterChildren(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        id.prettyPrint(s, prefix, true);
      expr.prettyPrint(s, prefix, true);
    }
    @Override public void decompile(IndentPrintStream s){
        
    }
    
    }

