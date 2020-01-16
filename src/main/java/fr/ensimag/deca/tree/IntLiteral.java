package fr.ensimag.deca.tree;
import java.util.*;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import org.apache.commons.lang.Validate;
import java.io.PrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
/**
 * Integer literal
 *
 * @author gl53
 * @date 01/01/2020
 */
public class IntLiteral extends AbstractExpr {
    public int getValue() {
        return value;
    }

    private int value;

    public IntLiteral(int value) {
        Validate.isTrue(value >= -2147483648,
                "int values connot be less than -2147483648");
        Validate.isTrue(value <= 2147483647,
                "int values connot depass +2147483648");
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        if (!compiler.getSymbols().checkSymbol("int")){
            throw new ContextualError("int Type is not yet implemented", this.getLocation());
        }
        /*
       Set<Symbol> sym = localEnv.stringIsIn();
       for (Symbol s:sym)
       {
           System.out.println(s.getName());
       }
                */
       Type returnType = new IntType(compiler.getSymbols().getSymbol("int"));
       this.setType(returnType);
       return this.getType();
    }


    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }
    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        GPRegister r = Register.getR(1);
        compiler.addInstruction(new LOAD(value, r));
        compiler.addInstruction(new WINT());
    }
    @Override
    protected void codeGenLoad(DecacCompiler compiler, GPRegister r1){
        int val = this.getValue();
        compiler.addInstruction(new LOAD(val, r1));
    }

}
