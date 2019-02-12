package ast;

import environment.Environment;
import scanner.ScanErrorException;

/**
 * @author Sahil Jain
 * @version 3/20/18
 * Class for the IF THEN ELSE statement that allows the parser to evaluate a if statement. It also
 * initializes the instance variables for an if statement (the condition, the program for then,
 * and the program for else)
 */
public class If extends Statement
{
    private Expression cond;
    private Program then;
    private Program elses;
    /**
     * Constructor for the if loop that initializes the condition, the statement for the case
     * that the condition is true, and the condition that the statement is false
     * @param cond The condition that will be evaluated. If true, then statement will be run;
     * else, the "elses" statement will be run
     * @param then The program that will be executed if the condition is true
     * @param elses The program that will be executed if the condition is false
     */
    public If(Expression cond, Program then, Program elses)
    {
        this.cond = cond;
        this.then = then;
        this.elses = elses;
    }
    /**
     * Constructor for the if loop that initializes the condition and the statement for the case
     * that the condition is true. Since this is an alternate constructor for the case that there
     * is no "ELSE", the "elses" statement is set to null
     * @param cond The condition that will be evaluated. If true, then statement will be run
     * @param then The program that will be executed if the condition is true
     */
    public If(Expression cond, Program then)
    {
        this.cond = cond;
        this.then = then;
        this.elses = null;
    }
    /**
     * Evaluates the if statement by running the then program if the condition is true; if the
     * condition is false, runs the "elses" program if the if statement has an ELSE
     * @param env The given environment
     * @throws scanner.ScanErrorException if the parser encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     */
    public void exec(Environment env) throws ScanErrorException,java.io.IOException
    {
        if (elses!=null)
        {
            if (cond.eval(env)==1)
            {
                then.exec(env);
            }
            else
            {
                elses.exec(env);
            }
        }
        else
        {
            if (cond.eval(env)==1)
            {
                then.exec(env);
            }
        }
    }

}
