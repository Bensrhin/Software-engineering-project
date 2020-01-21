package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.EnvironmentType.DoubleDefException;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 *
 * @author gl53
 * @date 01/01/2020
 */
public class DeclClass extends AbstractDeclClass {
    private AbstractIdentifier name;
    private AbstractIdentifier superName;
    private ListDeclField fields;
    private ListDeclMethod methods;

    public DeclClass(AbstractIdentifier name, AbstractIdentifier superName,
                     ListDeclField fields, ListDeclMethod methods)
    {
        this.name = name;
        this.superName = superName;
        this.fields = fields;
        this.methods = methods;
    }
    public AbstractIdentifier getName()
    {
        return this.name;
    }
    public AbstractIdentifier getSuperName()
    {
        return this.superName;
    }
    public ListDeclField getFields()
    {
        return this.fields;
    }
    public ListDeclMethod getMethods()
    {
        return this.methods;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        name.decompile(s);
        s.print(" extends ");
        superName.decompile(s);
        s.println();
        s.println("{");
        s.indent();
        fields.decompile(s);
        methods.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        Symbol nameKey = this.getName().getName();
        Symbol superNameKey = this.getSuperName().getName();
        Definition def = compiler.get_env_types().get(superNameKey);

        /*
        if (superNameKey.getName().equals("Object"))
        {
            loc = Location.BUILTIN;
        }
        */
        if(def == null)
        {
            throw new ContextualError("L'identificateur \""
                    + superName.decompile() + "\" non déclarée (règle 1.3)",
                    this.getLocation());
        }
        else if(!def.isClass())
        {
            throw new ContextualError("L'identificateur \""
                    + superName.decompile() + "\" n'est pas une class (règle 1.3)",
                    this.getLocation());
        }
        // Location loc = def.getLocation();
        // ClassType superType = new ClassType(superNameKey, loc, null);
        this.getSuperName().setDefinition((ClassDefinition) def);
        this.getSuperName().setType((ClassType) def.getType());

        ClassType currentType = new ClassType(nameKey,
                this.getName().getLocation(), (ClassDefinition) def);
        try
        {
            compiler.get_env_types().declare(nameKey, currentType.getDefinition());
        }
        catch (DoubleDefException e)
        {
            throw new ContextualError("L'identificateur \"" +
                    nameKey.toString()
                       + "\" est déjà déclaré (règle 1.3)", this.getLocation());
        }
        this.getName().setType(currentType);
        this.getName().setDefinition(currentType.getDefinition());
    }
    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {

          this.getFields().verifyListDeclField(compiler,
          this.getSuperName(), this.getName());
          this.getMethods().verifyListDeclMethod(compiler,
          this.getSuperName(), this.getName());
        //throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, false);
        superName.prettyPrint(s, prefix, false);
        fields.prettyPrint(s, prefix, true);
        methods.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        name.iter(f);
        superName.iter(f);
        fields.iter(f);
        methods.iter(f);
    }

}
