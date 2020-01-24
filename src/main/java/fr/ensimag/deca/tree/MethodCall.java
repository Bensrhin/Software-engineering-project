
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
        Type class2 = this.expr.verifyExpr(compiler, localEnv, currentClass);
        if (class2 == null || !class2.isClass())
        {
          throw new ContextualError("L'identificateur \"" + expr.decompile() +
          "\" n'est pas une classe (règle 3.71)", expr.getLocation());
        }
        EnvironmentExp exp2 = ((ClassType)class2).getDefinition().getMembers();

        // Definition def = compiler.get_env_types().get(class2.getName());
        this.id.verifyExpr(compiler, exp2, currentClass);
        Definition method0 = this.id.getDefinition();
        if (method0 == null || !method0.isMethod())
        {
          throw new ContextualError("L'identificateur \"" + id.decompile() +
          "\" n'est pas une méthode (règle 3.72)", id.getLocation());
        }
        MethodDefinition method = (MethodDefinition) method0;
        Signature sigExpected = method.getSignature();
        Signature sig = new Signature();
        Iterator<AbstractExpr> exprs = this.args.iterator();
            while (exprs.hasNext())
            {
                AbstractExpr expr = exprs.next();
                Type type = expr.verifyExpr(compiler, localEnv, currentClass);
                sig.add(type);
            }
        if (sig.size() == 0 && sigExpected.size() != 0)
        {
          throw new ContextualError("Veuillez inserer les " +
                          "paramètres pour la méthode \""
                          + expr.decompile() + "\" définie à " +
                          method.getLocation() + " règle(3.73)", this.getLocation());
        }
        if (!sig.equals(sigExpected))
        {
          throw new ContextualError("Veuillez inserer les bons types " +
                          "de paramètres pour la méthode \""
                          + expr.decompile() + "\" définie à " +
                          method.getLocation() + " règle(3.74)", this.getLocation());
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
        expr.decompile(s);
        
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
        id.codeGenAppMethode(compiler, r, args);
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
