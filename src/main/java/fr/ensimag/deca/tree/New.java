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
 * @author ensimag
 */
public class New extends AbstractExpr{
  private AbstractIdentifier idExpr;
  public New(AbstractIdentifier idExpr){
      this.idExpr = idExpr;
  }
  public AbstractIdentifier getIdExpr(){
    return this.idExpr;
  }
  @Override
  public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
          ClassDefinition currentClass) throws ContextualError {
        Type type =  this.getIdExpr().verifyType(compiler);
        if (!type.isClass())
        {
          throw new ContextualError("Le type doit être une classe (règle 3.42)", this.getLocation());
        }
        return type;
  }


  @Override
  public void decompile(IndentPrintStream s) {
      s.print("(");
      idExpr.decompile(s);
      s.print(")");


  }

  @Override
  protected void iterChildren(TreeFunction f) {
      // leaf node => nothing to do
      idExpr.iterChildren(f);
  }

  @Override
  protected void prettyPrintChildren(PrintStream s, String prefix) {
      // leaf node => nothing to do
      idExpr.prettyPrint(s, prefix, true);
  }
  @Override
  protected GPRegister codeGenLoad(DecacCompiler compiler){
        return null;
    }
}
