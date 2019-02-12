package ast;

import environment.Environment;
import scanner.ScanErrorException;
import java.util.ArrayList;

/**
 * The Condition class represents an Expression shown in the above grammar and is used to evaluate
 * the validity of statements for if and while loops. It extends Expression class and therefore
 * has the eval method, which returns 0 if the expression is false or 1 if the expression is true.
 * The constructor for the Condition class takes in an expression, representing the value that all
 * of the other expressions will be compared to, a list of Strings that contains a list of all
 * boolean operators to be used, and a list of Expressions that contains all of the other expressions
 * that will be compared to the first one.
 * @author Sahil Jain
 * @version 3/20/18
 */
public class Condition extends Expression
{
    private Expression exp1;
    private ArrayList<String> relops;
    private ArrayList<Expression> expressions;

    /**
     * Constructor for the Condition class that initializes the operator (either =, <>,>, <=, <,
     * or >=), the first expression, and the second expression
     * @param exp1
     * @param relops
     * @param expressions
     */
    public Condition(Expression exp1, ArrayList<String> relops, ArrayList<Expression> expressions)
    {
        this.exp1 = exp1;
        this.relops = relops;
        this.expressions = expressions;
    }

    /**
     * Evaluates the condition by comparing the first expression to the other expressions based
     * on the operators provided
     * @param env The given environment
     * @return an integer value (1 if statement is true, 0 if false)
     */
    public int eval(Environment env) throws ScanErrorException, java.io.IOException
    {
        for (int i = 0; i < relops.size() ; i++)
        {
            String relop = relops.get(i);
            Expression exp2 = expressions.get(i);
            if (relop.equals("="))
            {
                if (exp1.eval(env)!=exp2.eval(env))
                {
                    return 0;
                }
            }
            else if (relop.equals("<>"))
            {
                if (exp1.eval(env)==exp2.eval(env))
                {
                    return 0;
                }
            }
            else if (relop.equals(">"))
            {
                if (exp1.eval(env)<=exp2.eval(env))
                {
                    return 0;
                }
            }
            else if (relop.equals("<"))
            {
                if (exp1.eval(env)>=exp2.eval(env))
                {
                    return 0;
                }
            }
            else if (relop.equals("<="))
            {
                if (exp1.eval(env)>exp2.eval(env))
                {
                    return 0;
                }
            }
            else
            {
                if (exp1.eval(env)<exp2.eval(env))
                {
                    return 0;
                }
            }
        }
        return 1;

    }
}
