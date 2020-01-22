package fr.ensimag.deca.tree;
import java.util.Iterator;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
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
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {

    @Override
    public void decompile(IndentPrintStream s) {

      for (AbstractDeclMethod method : getList()) {
          method.decompile(s);
          s.println();
      }
    }

    /**
     * Implements non-terminal "list_decl_Method" of [SyntaxeContextuelle] in pass 3
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
    void verifyListDeclMethod(DecacCompiler compiler,
                AbstractIdentifier superIdentifier, AbstractIdentifier classIdentifier) throws ContextualError {
        Iterator<AbstractDeclMethod> declMethods = this.iterator();
        int index = superIdentifier.getClassDefinition().getNumberOfMethods();
        classIdentifier.getClassDefinition().setNumberOfMethods(index);
        while (declMethods.hasNext())
        {
            AbstractDeclMethod declMethod = declMethods.next();
            declMethod.verifyDeclMethod(compiler, superIdentifier, classIdentifier);
        }

    }
    void verifyListDeclMethodBody(DecacCompiler compiler,
        EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError
        {
          Iterator<AbstractDeclMethod> declMethods = this.iterator();
          while (declMethods.hasNext())
          {
              AbstractDeclMethod declMethod = declMethods.next();
              declMethod.verifyMethodBody(compiler, localEnv, currentClass);
          }
        }
    public void codeGenListMethod(DecacCompiler compiler){
        
        
    }


}
