package ast;
import environment.Environment;
import scanner.ScanErrorException;

/**
 * @author Sahil Jain
 * @version 3/20/18
 * The abstract class Expression models an Expression in the AST Parser. It has the abstract method
 * eval that is required in all classes that extend Expression(this method is supposed to
 * evaluate the current expression)
 */
public abstract class Expression
{
    /**
     * Abstract method eval is required in all classes that extend Expression. This method is
     * designed to evaluate the current expression.
     * @param env The given environment
     * @return the integer value of this Expression
     * @throws scanner.ScanErrorException if the parser encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     */
    public abstract int eval(Environment env) throws ScanErrorException, java.io.IOException;
}
