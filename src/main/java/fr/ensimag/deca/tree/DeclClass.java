package fr.ensimag.deca.tree;
import fr.ensimag.deca.context.EnvironmentType.DoubleDefException;
import java.util.*;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.*;
import java.util.*;
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
        }

    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        ClassDefinition currentClass = this.getName().getClassDefinition();
        EnvironmentExp localEnv = currentClass.getMembers();
        this.getFields().verifyListDeclFieldValue(compiler, localEnv, currentClass);
        this.getMethods().verifyListDeclMethodBody(compiler, localEnv, currentClass);
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
    @Override
    protected void codeGenClass(DecacCompiler compiler){
        if(superName.getType().toString().equals("Object")){
            superName.codeGenObj(compiler);
        }
        ClassDefinition current =  name.getClassDefinition();
        codeGenClass(compiler, current);


    }
    private Set<Symbol> vu = new HashSet<Symbol>();
    private Set<LabelOperand> labels = new HashSet<LabelOperand>();
    protected void codeGenClass(DecacCompiler compiler, ClassDefinition current){
       if(current.getType().getName() == this.name.getName()){
            compiler.addComment("construction de la table des methodes de " + current.getType());
            DAddr addr = current.getOperand();
            RegisterOffset gb = compiler.getRegisterManager().getRegOff();
            compiler.addInstruction(new LEA(addr, Register.R0));
            compiler.addInstruction(new STORE(Register.R0, gb));
            current.setOperand(gb);
        }
        Map<Symbol, ExpDefinition> dic = current.getMembers().getMapMethod();
        Set<Map.Entry<Symbol, ExpDefinition>> couples = dic.entrySet();
        Iterator<Map.Entry<Symbol, ExpDefinition>> itCouples = couples.iterator();
        ExpDefinition mth;
        while(itCouples.hasNext()){
            Map.Entry<Symbol, ExpDefinition> couple = itCouples.next();
            mth = couple.getValue();
            if(vu.add(couple.getKey())){
                DAddr addr = current.getOperand();
                MethodDefinition methode = (MethodDefinition)(mth);
                RegisterOffset gb0 = compiler.getRegisterManager().getRegOff();
                LabelOperand label = new LabelOperand(methode.getLabel());
                labels.add(label);
                compiler.addInstruction(new LOAD(label, Register.R0));
                compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(methode.getIndex(), Register.GB)));

            }
        }

        if(current.getSuperClass() != null){
            codeGenClass(compiler, current.getSuperClass());
        }
    }
    // protected void codeGenClass2(DecacCompiler compiler){
    //     //System.out.println(this.getName().getClassDefinition().toString());
    //     Label labelInit = new Label("init."+this.getName().getClassDefinition().getType().toString());
    //     compiler.addLabel(labelInit);
    //     fields.codeGenListField(compiler);
    //     //methods.codeGenListMethod(compiler);
    // }
    protected void codeGenField(DecacCompiler compiler, ClassDefinition current){
        if(current.getType().getName() == this.name.getName()){
            compiler.addComment("init." + current.getType().getName());
            if(fields.getList().size() > 0){
                compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), Register.R1));
                compiler.addInstruction(new LOAD(0, Register.R0));
            }
            for(AbstractDeclField i: fields.getList()){
                FieldDefinition fld =((Identifier)(((DeclField)(i)).getNameField())).getFieldDefinition();
                compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(fld.getIndex(), Register.R1)));
            }
        }
        if(current.getSuperClass() != null && !current.getSuperClass().getType().getName().toString().equals("Object")){
            System.out.println(current.getType().getName());
            codeGenField(compiler, current.getSuperClass());
            compiler.addInstruction(new PUSH(Register.R1));
            compiler.addInstruction(new BSR(new LabelOperand(new Label("init." + current.getSuperClass().getType()))));
            compiler.addInstruction(new SUBSP(1));
            compiler.addInstruction(new POP(Register.R1));
        }
        if(current.getType().getName() == this.name.getName()){
            fields.codeGenListField(compiler);
        }
    }

    @Override

    protected void codeGenClassPass2(DecacCompiler compiler){
        ClassDefinition current =  name.getClassDefinition();
        codeGenField(compiler, current);
        methods.codeGenListMethod(compiler);
    }

  }
