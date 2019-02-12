package ast;

import environment.Environment;
import scanner.ScanErrorException;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Sahil Jain
 * @version 5/27/18
 * The Display class represents a display statement in the ast parter, as denoted by the "display"
 * statement in the grammar. Its arguments include an expression, whose value is to be printed out,
 * and a String identification if there is a "read" segment in the display statement. The value that
 * the user inputs is to be stored in the variable that has the name given by identification. Since
 * this class extends the Statement class, it implements the exec method, which prints out the value
 * of the expression and stores the user input in the variable of the given name.
 */
public class Display extends Statement
{
    private Expression exp;
    private String id;

    /**
     * Constructor for the Display that sets up the instance variable exp and sets id to be null
     * @param exp the Expression to be printed
     */
    public Display(Expression exp)
    {
        this.exp = exp;
        id = null;
    }

    /**
     * Constructor for the Display that sets up the instance variables exp and id
     * @param exp The expression to be printed
     * @param id The name of the variable in which a value is to be read
     */
    public Display(Expression exp, String id)
    {
        this.exp = exp;
        this.id = id;
    }

    /**
     * The exec method of the Display object first evaluates the Expression in the environment and
     * prints out the value. Then, if there is a read portion of the statement, an integer value is
     * read. This value and the name of the variable are used to create a new variable.
     * @param env The given environment
     * @throws ScanErrorException if the current token does not equal to the given token
     * @throws java.io.IOException if there is any other basic error
     */
    @Override
    public void exec(Environment env) throws ScanErrorException, IOException
    {
        System.out.println(exp.eval(env));
        if (id != null)
        {
            System.out.println("Enter a number you want to be stored in "+ id);
            Scanner scannerread = new Scanner(System.in);
            int num = scannerread.nextInt();
            env.declareVariable(id, num);
        }
    }
}
