package ast;

import environment.Environment;

/**
 * The number class represents an integer and extends Expression. It has an eval method that
 * returns the value of the integer it has as its instance variable.
 * @author Sahil Jain
 * @version 3/20/18
 */
public class Number extends Expression
{
    private int value;

    /**
     * The constructor of the Number class that initializes the instance variable, which is an
     * integer
     * @param value the value of the integer
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Evaluates the expression/number by returning its value, the value of its integer instance
     * variable
     * @param env The given environment
     * @return the value of this number aka the value of its integer instance variable
     */
    public int eval(Environment env)
    {
        return value;
    }

}
