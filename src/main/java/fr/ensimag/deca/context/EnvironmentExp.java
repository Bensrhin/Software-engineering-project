package fr.ensimag.deca.context;
import java.util.*;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.util.Map;
import fr.ensimag.deca.context.Type;
/**
 * Dictionary associating identifier's ExpDefinition to their names.
 *
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 *
 * The dictionary at the head of this list thus corresponds to the "current"
 * block (eg class).
 *
 * Searching a definition (through method get) is done in the "current"
 * dictionary and in the parentEnvironment if it fails.
 *
 * Insertion (through method declare) is always done in the "current" dictionary.
 *
 * @author gl53
 * @date 01/01/2020
 */
public class EnvironmentExp {
    // A FAIRE : implémenter la structure de donnée représentant un
    // environnement (association nom -> définition, avec possibilité
    // d'empilement).

    EnvironmentExp parentEnvironment;
    protected Map<Symbol, ExpDefinition> dictionary;

    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
        dictionary = new HashMap<Symbol, ExpDefinition>();
    }

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public ExpDefinition get(Symbol key) {
        Symbol s = this.stringIsIn(key);
        if (s != null)
        {
            return dictionary.get(s);
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
    /** Compatibilté pour l'affectation */
    public boolean assignCompatible(Type t1, Type t2)
    {
        if ((t1.isFloat() & t2.isInt())||(this.subType(t2, t1)) )
        {
            return true;
        }
        return false;
    }
    /** Relation de sous-typage */
    public boolean subType(Type t1, Type t2)
    {
        if (t1.toString().equals(t2.toString()))
        {
            return true;
        }
        String obj = new String( (new Object()).getClass().getName() );
        String objT2 = new String( t2.getClass().getName() );
        if(obj.equals(objT2))
        {
            System.out.println(obj + objT2);
            return true;
        }
        /* todo */
        return false;
    }
    public Set<Symbol> stringIsIn()
    {
        return this.dictionary.keySet();
    }
    public Symbol stringIsIn(Symbol key)
    {
        Set<Symbol> sym = this.stringIsIn();
        for (Symbol s:sym)
        {
            if(s.getName().equals(key.toString()))
            {
                return s;
            }
        }
        return null;
    }
    public boolean isIn(Symbol key)
    {
        if (this.get(key) != null)
        {
            return true;
        }
        else
        {
            return false;
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
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {
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
