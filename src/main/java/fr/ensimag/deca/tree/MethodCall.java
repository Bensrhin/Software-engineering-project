
package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.Type;
import java.util.Iterator;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.instructions.OPP;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

/**
 *
 * @author
 */
public class MethodCall extends AbstractLValue{
    private AbstractExpr expr;
    private AbstractIdentifier id;
    private ListExpr args;
    public MethodCall(AbstractExpr expr, AbstractIdentifier id, ListExpr args){
       this.id = id;
       this.expr = expr;
       this.args = args;
    }
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        ClassType class2 = (ClassType)this.expr.verifyExpr(compiler, localEnv, currentClass);
        EnvironmentExp exp2 = class2.getDefinition().getMembers();

        Definition def = compiler.get_env_types().get(class2.getName());
        MethodDefinition method = (MethodDefinition) this.id.verifydef(exp2);
        Signature sigExpected = method.getSignature();
        Signature sig = new Signature();
        Iterator<AbstractExpr> exprs = this.args.iterator();
            while (exprs.hasNext())
            {
                AbstractExpr expr = exprs.next();
                Type type = expr.verifyExpr(compiler, localEnv, currentClass);
                sig.add(type);
            }
        if (!sig.equals(sigExpected))
        {
          throw new ContextualError("règele 3.74", this.getLocation());
        }
        this.setType(method.getType());
        return method.getType();
    }
    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iterChildren(f);
        id.iterChildren(f);
        args.iterChildren(f);

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, true);
        id.prettyPrint(s, prefix, true);
        args.prettyPrint(s, prefix, true);

    }
    @Override public void decompile(IndentPrintStream s){

    }
    @Override
    protected void codeGenPrint(DecacCompiler compiler, boolean hex){
        //throw new UnsupportedOperationException("not yet implemented44");
        System.out.println(id.getType());
        

        
    }
    @Override 
    protected GPRegister codeGenLoad(DecacCompiler compiler){
        compiler.addComment("appel de methode" + ((Identifier)(id)).getMethodDefinition().getIndex());
        compiler.addInstruction(new ADDSP(args.size() + 1));
        GPRegister r = expr.codeGenLoad(compiler);
        System.out.println(expr.getType());
        id.codeGenAppMethode(compiler, r);
        compiler.addInstruction(new SUBSP(args.size() + 1));
        compiler.getRegisterManager().freeReg(compiler, r);
        return Register.R0;
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler){
        //throw new UnsupportedOperationException("not yet implemented à m3alem");
        this.codeGenLoad(compiler);
    }
    }
