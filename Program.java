package ast;
import environment.Environment;
import scanner.ScanErrorException;

import java.util.ArrayList;

/**
 * The Program class represents a program in the parser. It initializes the list of statements in
 * this program.
 * @author Sahil Jain
 * @version 3/28/18
 */
public class Program
{
    private ArrayList<Statement> stmts;

    /**
     * Constructor for the Program class that intializes the list of statements
     * @param stmts the list of statements
     */
    public Program(ArrayList<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Exec executes this program by executing all of the statements
     * @param env the given environment
     * @throws scanner.ScanErrorException if the parser encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     */
    public void exec(Environment env) throws ScanErrorException, java.io.IOException
    {
        for (Statement stmt:stmts)
        {
            stmt.exec(env);
        }
    }
}
