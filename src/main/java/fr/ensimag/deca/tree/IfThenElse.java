package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl53
 * @date 01/01/2020
 */
public class IfThenElse extends AbstractInst {

    private final AbstractExpr condition;
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public AbstractExpr getCondition()
    {
        return this.condition;
    }
    public ListInst getThen()
    {
        return this.thenBranch;
    }

    public ListInst getElse()
    {
        return this.elseBranch;
    }
    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    public void setElseBranch(ListInst elsebranch){
        this.elseBranch = elsebranch;
    }
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.getCondition().verifyCondition(compiler, localEnv, currentClass);
        this.getThen().verifyListInst(compiler, localEnv, currentClass, returnType);
        this.getElse().verifyListInst(compiler, localEnv, currentClass, returnType);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
      s.print("if(");
      this.getCondition().decompile(s);
      s.println(")");
      s.println("{");
      s.indent();
      this.getThen().decompile(s);
      //s.println();
      s.unindent();
      s.println("}");
      s.println("else");
      s.println("{");
      s.indent();
      this.getElse().decompile(s);
      s.println();
      s.unindent();
      s.println("}");    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
