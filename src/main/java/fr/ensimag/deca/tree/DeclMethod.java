package fr.ensimag.deca.tree;
import java.util.*;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.*;
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
    private AbstractMain methodBody;
    final private String code;

    private static final Logger LOG = Logger.getLogger(DeclMethod.class);
    public DeclMethod(AbstractIdentifier type, AbstractIdentifier method,
        ListDeclParam params, AbstractMain methodBody) {
        Validate.notNull(type);
        Validate.notNull(method);
        Validate.notNull(params);
        Validate.notNull(methodBody);

        this.type = type;
        this.method = method;
        this.params = params;
        this.methodBody = methodBody;
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
        this.methodBody = null;
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
    protected void verifyDeclMethod(DecacCompiler compiler,
            AbstractIdentifier superIdentifier) throws ContextualError {
            Type type = this.getNameType().verifyType(compiler);
            // Signature sig = this.getParams().verifyListDeclParam(compiler);
            Signature sig;
            Boolean isClass = compiler.get_env_types().get(superIdentifier.getName()).isClass();
            MethodDefinition override = (MethodDefinition)superIdentifier.getClassDefinition().getMembers().get(getNameMethod().getName());

            if (isClass && override != null &&
                !((override instanceof MethodDefinition) &&
                  // override.getSignature().equals(sig) &&
                  compiler.get_env_types().subType(type, override.getType())))
            {
              throw new ContextualError("Méthode \""
              + this.getNameMethod().getName().getName() + "\" est redéfinie " +
              "dans une super classe avec une autre définition (règle 2.7)",
              this.getLocation());
            }
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
            methodBody.iter(f);
        }
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        method.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, true);
        if (code == null)
        {
          methodBody.prettyPrint(s, prefix, true);
        }
        else
        {
          //code.prettyPrint(s, prefix, true);
        }
    }
}
