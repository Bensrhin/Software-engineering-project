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
public class DeclField extends AbstractDeclField {


    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    final private AbstractInitialization initialization;
    private static final Logger LOG = Logger.getLogger(DeclField.class);
    public DeclField(AbstractIdentifier type, AbstractIdentifier fieldName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(fieldName);
        Validate.notNull(initialization);
        this.type = type;
        this.fieldName = fieldName;
        this.initialization = initialization;
    }

    public AbstractIdentifier getNameType()
    {
        return this.type;
    }
    public AbstractIdentifier getNameField()
    {
        return this.fieldName;
    }
    public AbstractInitialization getInitialization()
    {
        return this.initialization;
    }
    @Override
    protected void verifyDeclField(DecacCompiler compiler, ClassDefinition superClass,
            ClassDefinition currentClass)
            throws ContextualError {
            /*
              Type nameType = this.getNameType().verifyType(compiler);
              if (nameType.isVoid())
              {
                  throw new ContextualError("type must be defferent than void", this.getLocation());
              }
              
              FieldiableDefinition def = new FieldiableDefinition(nameType, this.getLocation());
              Symbol symbol = this.getNameField().getName();
              try
              {
                  localEnv.declare(symbol, def);
              }
              catch (DoubleDefException e)
              {
                  throw new ContextualError(symbol.toString()
                             + "is already defined", this.getLocation());
              }
              Type nameField = this.getNameField().verifyExpr(compiler, localEnv, currentClass);
              this.getInitialization().verifyInitialization(compiler, nameType, localEnv, currentClass);
              //LOG.debug("End of verifyDeclField");
    }       */
    }
    @Override
    protected void codeGenField(DecacCompiler compiler, int i)
    {
        
    }

    @Override
    public void decompile(IndentPrintStream s) {
      this.type.decompile(s);
      s.print(" ");
      this.fieldName.decompile(s);
      this.initialization.decompile();
      s.print(";");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        fieldName.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
