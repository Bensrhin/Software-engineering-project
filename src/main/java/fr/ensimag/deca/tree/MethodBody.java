package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.Label;

/**
 * @author gl53
 * @date 01/01/2020
 */
public class MethodBody extends Tree {
    private static final Logger LOG = Logger.getLogger(MethodBody.class);

    private ListDeclVar declVariables;
    private ListInst insts;
    public MethodBody(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }


    protected void verifyBody(DecacCompiler compiler, EnvironmentExp localEnv,
              EnvironmentExp paramEnv, ClassDefinition currentClass,
              Type rType) throws ContextualError
      {
        paramEnv.setParent(localEnv);
        declVariables.verifyListDeclVariable(compiler, paramEnv, currentClass);
        insts.verifyListInst(compiler, paramEnv, currentClass, rType);
      }
    public ListDeclVar getDeclVariables(){
        return this.declVariables;

    }

    protected void codeGenBody(DecacCompiler compiler) {

    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
    // public void codeGenEntete(DecacCompiler compiler, int n){
    //     if(n > 0){
    //         Label pilePleine= new Label("pile_pleine");
    //         if (!compiler.getCompilerOptions().getNoCheck()){
    //             compiler.addInstruction(new TSTO(n));
    //             compiler.addInstruction(new BOV(pilePleine));
    //         }
    //         compiler.addInstruction(new ADDSP(n));
    //     }
    // }
}
