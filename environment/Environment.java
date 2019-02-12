package environment;
import java.util.HashMap;
import java.util.Map;
import ast.Statement;
/**
 * @version 3/20/18
 * @author Sahil Jain
 * The environment class contains the instance variables and methods neccessary to access and
 * store variables for the ast parser.
 */
public class Environment
{
    private Map<String, Integer> vars;

    /**
     * The constructor for the environment class that initializes the HashMap with the key of
     * variable names and the Integer of the variable values.
     */
    public Environment()
    {
        vars = new HashMap<String, Integer>();
    }



    /**
     * Returns the value associated with the given variable name.
     * @param variable the given variable name
     * @return the integer value associated with the given variable name
     */
    public int getVariable(String variable)
    {
        return vars.get(variable);
    }
    /**
     * Adds a new variable with a name,value pair to the hashmap
     * @param variable The name of the variable that is to be added
     * @param num The value of the variable that is to be added
     */
    public void declareVariable(String variable, int num)
    {
        vars.put(variable,num);
    }
}
