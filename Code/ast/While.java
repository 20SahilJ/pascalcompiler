package ast;

import environment.Environment;
import scanner.ScanErrorException;

/**
 * @author Sahil Jain
 * @version 3/20/18
 * Class for the while loop that allows the parser to evaluate a while loop. It also initializes
 * the instance variables for a while loop (the condition and the program).
 */
public class While extends Statement
{
    private Expression cond;
    private Program program;

    /**
     * Constructor for the while loop that initializes the condition and the program that is to
     * be executed repeatedly
     * @param cond The condition that will be evaluated. If true, the corresponding statement
     * will be executed repeatedly until the condition is not true
     * @param program The program that will be executed repeatedly while the condition is true
     */
    public While(Expression cond, Program program)
    {
        this.cond = cond;
        this.program = program;
    }
    /**
     * Evaluates the while loop by repeatedly running the program while the condition is true
     * @param env The given environment
     * @throws scanner.ScanErrorException if the parser encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     */
    public void exec(Environment env) throws ScanErrorException, java.io.IOException
    {
        while (cond.eval(env)==1)
        {
            program.exec(env);
        }
    }
}
