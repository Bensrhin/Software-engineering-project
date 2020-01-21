package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.List;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl53
 * @date 01/01/2020
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void add(Type t) {
        args.add(t);
    }

    public Type paramNumber(int n) {
        return args.get(n);
    }

    public int size() {
        return args.size();
    }
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) {
    //         return true;
    //     }
    //     if (o == null || getClass() != o.getClass()) {
    //         return false;
    //     }
    //     Signature sig = (Signature) o;
    //     if (!(size() == sig.size() && paramNumber() == sig.paramNumber())){
    //         return false;
    //     }
        // ListIterator<Type> e1 = args.listIterator();
        // ListIterator<Type> e2 = (sig.args).listIterator();
        // while (e1.hasNext() && e2.hasNext()) {
        //      Type o1 = e1.next();
        //      Type o2 = e2.next();
        //      if (!o1.sameType(o2))
        //          return false;
        // }
        // return  true;
        // }

}
