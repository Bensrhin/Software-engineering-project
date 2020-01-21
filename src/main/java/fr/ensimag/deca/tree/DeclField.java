package fr.ensimag.deca.tree;

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
public class DeclField extends AbstractDeclField {

    final private Visibility visib;
    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    private AbstractInitialization initialization;
    private static final Logger LOG = Logger.getLogger(DeclField.class);
    public DeclField(Visibility visib, AbstractIdentifier type,
                     AbstractIdentifier fieldName,
                     AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(visib);
        Validate.notNull(fieldName);
        Validate.notNull(initialization);
        this.visib = visib;
        this.type = type;
        this.fieldName = fieldName;
        this.initialization = initialization;
    }

    public Visibility getVisibility()
    {
        return this.visib;
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
    protected void verifyDeclField(DecacCompiler compiler,
                  AbstractIdentifier superIdentifier, AbstractIdentifier classIdentifier)
            throws ContextualError {

              Type nameType = this.getNameType().verifyType(compiler);
              if (nameType.isVoid())
              {
                  throw new ContextualError("Type de l'indentificateur \""+
                          this.getNameField().getName().toString() +
                          "\" doit être différent de void (règle 2.5)", this.getLocation());
              }
              Boolean isClass = compiler.get_env_types().get(superIdentifier.getName()).isClass();
              Definition override = superIdentifier.getClassDefinition().getMembers().get(getNameField().getName());
              if (isClass && override != null && !(override instanceof FieldDefinition))
              {
                throw new ContextualError("Le champs \""
                + this.getNameField().getName().getName() + "\" est défini " +
                "dans une super classe avec une autre définition (règle 2.5)",
                this.getLocation());
              }
              ClassDefinition classDef = classIdentifier.getClassDefinition();
              int index = classDef.getNumberOfFields(); index ++;
              classDef.setNumberOfFields(index);
              FieldDefinition def = new FieldDefinition(nameType,
                      this.getLocation(), this.getVisibility(),
                      classDef, index);
              Symbol symbol = this.getNameField().getName();
              try
              {
                  classDef.getMembers().declare(symbol, def);
              }
              catch (DoubleDefException e)
              {
                  throw new ContextualError(symbol.toString()
                             + "is already defined", this.getLocation());
              }
              this.getNameField().setDefinition(def);
              this.getNameField().setType(nameType);
              // System.out.println(classDef.getNumberOfFields());
    }
    @Override
    protected void codeGenField(DecacCompiler compiler, int i)
    {

    }
    @Override
    String printNodeLine(PrintStream s, String prefix, boolean last,
            boolean inlist, String nodeName) {
            return super.printNodeLine(s, prefix, last, inlist,
                      "[visibility=" + visib.getValue() + "] " + nodeName);
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
