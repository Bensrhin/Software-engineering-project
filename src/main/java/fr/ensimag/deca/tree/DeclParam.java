package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
/**
 * @author gl53
 * @date 01/01/2020
 */
public class DeclParam extends AbstractDeclParam
{
    final private AbstractIdentifier type;
    final private AbstractIdentifier param;

    
    private static final Logger LOG = Logger.getLogger(DeclParam.class);
    public DeclParam(AbstractIdentifier type, AbstractIdentifier param) {
        Validate.notNull(type);
        Validate.notNull(param);
        this.type = type;
        this.param = param;
    }

    public AbstractIdentifier getNameType()
    {
        return this.type;
    }
    public AbstractIdentifier getParam()   {
        return this.param;
    }

    @Override
    protected void verifyDeclParam(DecacCompiler compiler) throws ContextualError {
            /*
              Type nameType = this.getNameType().verifyType(compiler);
              if (nameType.isVoid())
              {
                  throw new ContextualError("type must be defferent than void", this.getLocation());
              }
              
                parameDefinition def = ne    parameDefinition(nameType, this.getLocation());
              Symbol symbol = this.getNa    parametName();
              try
              {
                  localEnv.declare(symbol, def);
              }
              catch (DoubleDefException e)
              {
                  throw new ContextualError(symbol.toString()
                             + "is already defined", this.getLocation());
              }
              Type na   paramhis.getNa   paramerifyExpr(compiler, localEnv, currentClass);
              this.getInitialization().verifyInitialization(compiler, nameType, localEnv, currentClass);
              //LOG.debug("End of verifyDe  param */   
    }
    @Override
    protected void codeGenParam(DecacCompiler compiler, int i){
            param.codeGenIdent(compiler, i);
        
    }

    @Override
    public void decompile(IndentPrintStream s) {
      this.type.decompile(s);
      s.print(" ");
      this.param.decompile(s);
      s.print(";");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        param.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        param.prettyPrint(s, prefix, false);
    }
}
