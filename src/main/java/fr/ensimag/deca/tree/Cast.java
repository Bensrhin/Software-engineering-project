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
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.INT;

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
            throw new ContextualError("Le Cast: [ ( " +
            type.toString() + " ) " + " ( " +type2.toString() +  
            " ) ] n'est pas autorisée (règle 3.39)", this.getLocation());
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
  @Override
  protected void codeGenLoad(DecacCompiler compiler, GPRegister r1){
        expr.codeGenLoad(compiler, r1);
        if(this.getType().isInt()){
            compiler.addInstruction(new INT(r1, r1));
        }
        else{
            compiler.addInstruction(new FLOAT(r1, r1));
        }
    }

}
