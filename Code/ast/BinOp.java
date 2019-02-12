package ast;

import environment.Environment;
import scanner.ScanErrorException;

/**
 * The BinOp class represents an expression that has an operator with an expression on either
 * side. Since it extends the expression class, there is also an evaluate method that evaluates
 * this expression/binOP
 * @author Sahil Jain
 * @version 3/20/18
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructor for the BinOp that initializes the operator and the two expressions
     * @param op The operator
     * @param exp1 the expression to the left of the operator
     * @param exp2 the expression to the right of the operator
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluates this BinOp by performing the appropriate operator to the two expressions
     * @param env The given environment
     * @return the integer value of this BinOp
     */
    public int eval(Environment env) throws ScanErrorException, java.io.IOException
    {
        if (op.equals("*"))
        {
            return exp1.eval(env)*exp2.eval(env);
        }
        else if (op.equals("/"))
        {
            return exp1.eval(env)/exp2.eval(env);
        }
        else if (op.equals("+"))
        {
            return exp1.eval(env)+exp2.eval(env);
        }
        else
        {
            return exp1.eval(env) - exp2.eval(env);
        }
    }
}
