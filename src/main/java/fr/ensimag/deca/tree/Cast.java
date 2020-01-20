package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 *
 * @author gl53
 * @date 01/01/2020
 */
public class Cast extends AbstractLValue {
  private AbstractIdentifier idExpr;
  private AbstractExpr expr;
  public Cast(AbstractIdentifier idExpr, AbstractExpr expr){
      this.idExpr = idExpr;
      this.expr = expr;
  }
  public AbstractIdentifier getIdExpr(){
    return this.idExpr;
  }
  public AbstractExpr getExpr(){
    return this.expr;
  }
  @Override
  public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
          ClassDefinition currentClass) throws ContextualError {
        Type type = this.getIdExpr().verifyType(compiler);
        Type type2 = this.getExpr().verifyExpr(compiler, localEnv, currentClass);
        if(!localEnv.castCompatible(type2, type))
        {
            throw new ContextualError("Le cast n'est pas autorisé", this.getLocation());
        }
        this.setType(type);
        return type;
  }


  @Override
  public void decompile(IndentPrintStream s) {

  }

  @Override
  protected void iterChildren(TreeFunction f) {
      // leaf node => nothing to do
      idExpr.iterChildren(f);
      expr.iterChildren(f);
  }

  @Override
  protected void prettyPrintChildren(PrintStream s, String prefix) {
      // leaf node => nothing to do
      idExpr.prettyPrint(s, prefix, true);
      expr.prettyPrint(s, prefix, true);
  }


}