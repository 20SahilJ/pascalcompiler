package ast;

import environment.Environment;

/**
 * @author Sahil Jain
 * @version 3/20/18
 * The variable class represents a variable in an ast parser and extends the Expression class. It
 * contains an evaluate method that returns the value of the variable and a constructor that
 * initializes the name of the variable.
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Constructor for the Variable class that initializes the name of the current variable
     * @param name the name for this variable
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * The eval method evaluates this variable by returning the value associated with the
     * variable name in the environment
     * @param env The given environment
     * @return the integer value representing the value of the variable
     */
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
