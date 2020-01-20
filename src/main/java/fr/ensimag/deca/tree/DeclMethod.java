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
public class DeclMethod extends AbstractDeclMethod
{
    final private AbstractIdentifier type;
    final private AbstractIdentifier method;
    final private ListDeclParam params;
    private ListDeclVar declVariables;
    private ListInst insts;
    final private String code;
    
    private static final Logger LOG = Logger.getLogger(DeclMethod.class);
    public DeclMethod(AbstractIdentifier type, AbstractIdentifier method, 
        ListDeclParam params, ListDeclVar declVariables, ListInst insts) {
        Validate.notNull(type);
        Validate.notNull(method);
        Validate.notNull(params);
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.type = type;
        this.method = method;
        this.params = params;
        this.declVariables = declVariables;
        this.insts = insts;
        this.code = null;
    }
    public DeclMethod(AbstractIdentifier type, AbstractIdentifier method, 
        ListDeclParam params, String code) {
        Validate.notNull(type);
        Validate.notNull(method);
        Validate.notNull(params);
        Validate.notNull(code);
        
        this.type = type;
        this.method = method;
        this.params = params;
        this.code = code;
        this.insts = null;
    }

    public AbstractIdentifier getNameType()
    {
        return this.type;
    }
    public AbstractIdentifier getNameMethod()   {
        return this.method;
    }
    public ListDeclParam getParams()   {
        return this.params;
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, ClassDefinition supermethod) throws ContextualError {
            /*
              Type nameType = this.getNameType().verifyType(compiler);
              if (nameType.isVoid())
              {
                  throw new ContextualError("type must be defferent than void", this.getLocation());
              }
              
              methodeDefinition def = new methodeDefinition(nameType, this.getLocation());
              Symbol symbol = this.getNamemethodetName();
              try
              {
                  localEnv.declare(symbol, def);
              }
              catch (DoubleDefException e)
              {
                  throw new ContextualError(symbol.toString()
                             + "is already defined", this.getLocation());
              }
              Type namemethodhis.getNamemethoderifyExpr(compiler, localEnv, currentClass);
              this.getInitialization().verifyInitialization(compiler, nameType, localEnv, currentClass);
              //LOG.debug("End of verifyDeclmethod */   
    }
    @Override
    protected void codeGenMethod(DecacCompiler compiler, int i){
        //method.codeGenIdent(compiler, i);
        
    }

    @Override
    public void decompile(IndentPrintStream s) {
      this.type.decompile(s);
      s.print(" ");
      this.method.decompile(s);
      this.params.decompile();
      s.print(";");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        method.iter(f);
        params.iter(f);
        if (code == null)
        {
            declVariables.iter(f);
            insts.iter(f);
        }
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        method.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, true);
        if (code == null)
        {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
        }
        else
        {
            
        }
    }
}
