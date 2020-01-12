package fr.ensimag.deca.context;
import java.util.HashMap;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.util.Map;
/**
 * Dictionary associating identifier's TypeDefinition to their names.
 *
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).

 * @author gl53
 * @date 01/01/2020
 */
public class EnvironmentType {
    // implémenter la structure de donnée représentant un
    // environnement (association Symbol -> TypeDefinition

    EnvironmentType parentEnvironment;
    protected Map<Symbol, TypeDefinition> dictionary;

    public EnvironmentType(EnvironmentType parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
        dictionary = new HashMap<Symbol, TypeDefinition>();

    }

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public TypeDefinition get(Symbol key) {
        if (dictionary.containsKey(key))
        {
            return dictionary.get(key);
        }
        else
        {
            if (this.parentEnvironment != null)
            {
                return this.parentEnvironment.get(key);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     *
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary
     * - or, hides the previous declaration otherwise.
     *
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, TypeDefinition def) throws DoubleDefException {
        //throw new UnsupportedOperationException("not yet implemented");
        if (dictionary.containsKey(name))
        {
            throw new DoubleDefException();
        }
        else
        {
            dictionary.put(name, def);
        }
    }

}
