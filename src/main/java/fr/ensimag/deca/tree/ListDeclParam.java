package fr.ensimag.deca.tree;
import java.util.Iterator;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

/**
 * List of declarations (e.g. int x; float y,z).
 *
 * @author gl53
 * @date 01/01/2020
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {

    @Override
    public void decompile(IndentPrintStream s) {

      for (AbstractDeclParam param : getList()) {
          param.decompile(s);
          s.println();
      }
    }

    /**
     * Implements non-terminal "list_decl_Param" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to
     *      the "env_exp_r" attribute
     * @param currentClass
     *          corresponds to "class" attribute (null in the main bloc).
     */
    Signature verifyListDeclParam(DecacCompiler compiler) throws ContextualError {
        Iterator<AbstractDeclParam> declParams = this.iterator();
        Signature sig = new Signature();
        while (declParams.hasNext())
        {
            AbstractDeclParam declParam = declParams.next();
            sig.add(declParam.verifyDeclParam(compiler));
        }
        return sig;
    }
    void verifyParams(DecacCompiler compiler,
        EnvironmentExp paramEnv)  throws ContextualError
        {
          Iterator<AbstractDeclParam> declParams = this.iterator();
          while (declParams.hasNext())
          {
              AbstractDeclParam declParam = declParams.next();
              declParam.verifyParam(compiler, paramEnv);
          }
        }
    public void codeGenListParam(DecacCompiler compiler){
        int j = 1;
        int n = getList().size();
        if(n > 0){
            compiler.addInstruction(new TSTO(getList().size()));
            compiler.addInstruction(new ADDSP(getList().size()));
        }
        for (AbstractDeclParam i : getList()) {
            i.codeGenParam(compiler);
        }
    }


}
