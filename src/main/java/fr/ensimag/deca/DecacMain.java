package fr.ensimag.deca;

import fr.ensimag.deca.tree.AbstractProgram;
import java.io.File;
import java.io.PrintStream;
import org.apache.log4j.Logger;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tree.LocationException;


/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl53
 * @date 01/01/2020
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);

    public static void main(String[] args) throws ContextualError {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            //throw new UnsupportedOperationException("decac -b not yet implemented");
            System.out.println("Compilateur DECA. Equipe GL53.");
        }
        if (options.getSourceFiles().isEmpty() && !options.getPrintBanner()) {
            throw new UnsupportedOperationException("decac without argument not yet implemented");
        }
        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            throw new UnsupportedOperationException("Parallel build not yet implemented");
        }
        if (options.getParse()){
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.decompile()){
                   error = true;
                }
            }
        }
        if (options.getVerification()){
            PrintStream err = System.err;
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                try{
                    if (compiler.verify()){
                        error = true;
                    }
                }
                catch (ContextualError e) {
                    e.display(err);
                    error = true;
                    throw e;
                }

        }}
        if (options.getNoCheck()){
            System.out.println("No Check not "
                    + "implemented, nta a nabil, nta.");
            error = true;
        }
        else if (!options.getParse() && !options.getVerification()){
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
