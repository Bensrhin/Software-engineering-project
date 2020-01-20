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
public class ListDeclField extends TreeList<AbstractDeclField> {

    @Override
    public void decompile(IndentPrintStream s) {

      for (AbstractDeclField field : getList()) {
          field.decompile(s);
          s.println();
      }
    }

    /**
     * Implements non-terminal "list_decl_field" of [SyntaxeContextuelle] in pass 3
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
    void verifyListDeclField(DecacCompiler compiler, ClassDefinition superClass,
                             ClassDefinition currentClass) throws ContextualError {
        Iterator<AbstractDeclField> declFields = this.iterator();
        while (declFields.hasNext())
        {
            AbstractDeclField declField = declFields.next();
            declField.verifyDeclField(compiler, superClass, currentClass);
        }

    }
    public void codeGenListField(DecacCompiler compiler){
        int j = 1;
        int n = getList().size();
        if(n > 0){
            compiler.addInstruction(new TSTO(getList().size()));
            compiler.addInstruction(new ADDSP(getList().size()));
        }
        for (AbstractDeclField i : getList()) {
            i.codeGenField(compiler, j);
            j ++;
        }
    }


}
