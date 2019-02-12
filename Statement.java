package ast;
import environment.Environment;
import scanner.ScanErrorException;
/**
 * @author Sahil Jain
 * @version 3/20/18
 * The abstract class Statement models a Statement in the AST Parser. It has the abstract method
 * exec that is required in all classes that extend Statement(this method is supposed to
 * execute the current statement)
 */
public abstract class Statement
{
    /**
     * Abstract method exec is required in all classes that extend Statement. This method is
     * designed to execute the current statement.
     * @param env The given environment
     * @throws scanner.ScanErrorException if the parser encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     */
    public abstract void exec(Environment env) throws ScanErrorException, java.io.IOException;
}
