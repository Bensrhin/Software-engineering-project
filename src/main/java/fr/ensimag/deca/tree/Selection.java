/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
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
public class Selection extends AbstractLValue{
    private AbstractExpr expr;
    private AbstractIdentifier id;
    public Selection(AbstractExpr expr, AbstractIdentifier id){
       this.id = id;
       this.expr = expr;
    }
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        ClassType class2 = (ClassType)this.expr.verifyExpr(compiler, localEnv, currentClass);
        EnvironmentExp exp2 = class2.getDefinition().getMembers();

        Definition def = compiler.get_env_types().get(class2.getName());
        if (def == null)
        {
          throw new ContextualError(class2.getName() + " is not yet declared ",
            this.getLocation());
        }
        FieldDefinition field = (FieldDefinition) this.id.verifydef(exp2);
        if (field.getVisibility().getValue().equals("PUBLIC") && !def.isClass())
        {
          throw new ContextualError(class2.getName() + " is not a class ",
            this.getLocation());
        }
        return field.getType();
    }
    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iterChildren(f);
        id.iterChildren(f);

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
      expr.prettyPrint(s, prefix, true);
        id.prettyPrint(s, prefix, true);

    }
    @Override public void decompile(IndentPrintStream s){

    }

    }
