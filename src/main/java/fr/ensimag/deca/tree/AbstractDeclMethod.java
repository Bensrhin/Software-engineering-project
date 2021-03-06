package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * Method declaration
 *
 * @author gl53
 * @date 01/01/2020
 */
public abstract class AbstractDeclMethod extends Tree {

    /**
     * Implements non-terminal "decl_Method" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains "env_types" attribute
     * @param localEnv
     *   its "parentEnvironment" corresponds to the "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to
     *      the synthetized attribute
     * @param currentClass
     *          corresponds to the "class" attribute (null in the main bloc).
     */
    protected abstract void verifyDeclMethod(DecacCompiler compiler,
            AbstractIdentifier superIdentifier, AbstractIdentifier classIdentifier) throws ContextualError;
    protected abstract void verifyMethodBody(DecacCompiler compiler,
        EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError;

    protected abstract void codeGenMethod(DecacCompiler compiler);
    public abstract AbstractIdentifier getNameMethod();
}
