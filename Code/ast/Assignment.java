package ast;
import environment.Environment;
import scanner.ScanErrorException;
/**
 * @author Sahil Jain
 * @version 3/20/18
 * The assignment class represents an assignment in an ast parser. It extends the Statement
 * class, and therefore implements the exec method. In addition, it initializes the instance
 * variables of a String variable name and an expression as the value of the variable.
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Constructor for the Assignment class that takes in the name and value of the variable
     * @param var the name of the variable
     * @param exp the value of the variable, which is an expression
     */
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Executes the assignment by adding this variable to the environment
     * @param env The given environment
     * @throws ScanErrorException if the current token does not equal to the given token
     * @throws java.io.IOException if there is any other basic error
     */
    public void exec(Environment env) throws ScanErrorException, java.io.IOException
    {
        env.declareVariable(var,exp.eval(env));
    }
}
