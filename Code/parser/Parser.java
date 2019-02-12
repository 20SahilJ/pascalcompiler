package parser;
import ast.*;
import ast.Number;
import environment.Environment;
import scanner.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sahil Jain
 * @version 5/27/18
 * Parser is a class that parses a stream of tokens returned by the Scanner as described by the above
 * grammar. The constructor takes in a scanner as input for the so that it can obtain the tokens.
 * The class contains many methods (which will be described in more detail below) that are denoted
 * by parseX, where x is the object that the parser is currently parsing. As an example, the
 * parseStatement method parses a Statement as denoted by the Statement section in the grammar
 * above and  returns a Statement with all of the necessary instance variables. Please note that,
 * while most of the parseX methods are recursive, some utilize the while loop to repeatedly parse X.

 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;
    /**
     * Constructor for the Parser class. It takes in a Scanner and uses it to initialize the
     * scanner instance variable. The currentToken is defined to be the next Token in the scanner
     * and a HashMap for variables is initialized so that the key is a String and the value is an
     * integer.
     * @param scannerinput The given scanner
     * @throws scanner.ScanErrorException if other methods have errors in scanning or parsing the
     * input
     * @throws java.io.IOException if there is any other basic error
     */
    public Parser(Scanner scannerinput) throws scanner.ScanErrorException, java.io.IOException
    {
        scanner = scannerinput;
        currentToken = scanner.nextToken();
    }

    /**
     * Method: eat
     * The eat method sets the nextToken to be the next token
     * in the file if the given token is equal to the current token
     * @throws ScanErrorException if the current token does not equal to the given token
     * @throws java.io.IOException if there is any other basic error
     * @param expected the token that the current token should be compared to
     */
    private void eat(String expected) throws scanner.ScanErrorException, java.io.IOException
    {
        if (currentToken.equals(expected))
        {
            currentToken = scanner.nextToken();
        }
        else
        {
            throw new IllegalArgumentException("the token equals " + currentToken + " and it " +
                    "should " +
                    "equal " + expected);
        }
    }

    /**
     * The parseProgram method parses a program according to the grammar above. It uses a while loop
     * to repeatedly parse a Statement while the currentToken is either "display", "assign", "while,"
     * or "if," which are all of the possible starting tokens for a statement. After each iteration
     * of the while loop, the Statement is added to the ArrayList of Statements. At the end of the
     * method, a Program is returned with its argument being the ArrayList of Statements.
     * @return a program with a list of statements
     * @throws ScanErrorException if the current token does not equal to the given token
     * @throws java.io.IOException if there is any other basic error
     */
    private Program parseProgram() throws scanner.ScanErrorException, java.io.IOException
    {
        ArrayList<Statement> stmts = new ArrayList<Statement>();
        while(currentToken.equals("display")||currentToken.equals("assign")||currentToken.equals
                ("while")||currentToken.equals("if"))
            stmts.add(parseStatement());
        return new Program(stmts);
    }


    /**
     * parseNumber is a method that parses the given number, removing all parenthesis and
     * evaluating each - sign to be -1 multiplied by the number. It returns a number with the
     * value of the parsed integer.
     * precondition: current token is an integer
     * postcondition: number token has been eaten
     * @return a Number object with the value of the parsed integer
     * @throws scanner.ScanErrorException if the parseNumber encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     */
    private Number parseNumber() throws scanner.ScanErrorException, java.io.IOException
    {
        int num = Integer.parseInt(currentToken);
        eat(currentToken);
        return new Number(num);
    }
    /**
     *The parseStatement method parses a Statement according to the grammar above. It uses multiple
     * if statements to check what type of statement the current token denotes. (Note that if the
     * current token does not denote any type of statement in the grammar, a ScanErrorException is
     * thrown detailing the current token and what it should have been.) If the current token is
     * "display," the expression to be displayed is parsed and added as an argument for the "Display"
     * object. Then, if the current token is "read," an additional String representing the id of the
     * variable whose value is to be changed is parsed and added as a parameter to the "Display"
     * object. For an if statement, an Expression, representing the condition, and a program,
     * representing the statements that should be executed if the expression is evaluation to be true,
     * are parsed and added as arguments for the "If" object. If an else token is encountered, an
     * extra program is parsed and added as an argument for the object. A while statement requires
     * both an Expression, representing the condition required to be met, and a Program. Both of
     * these components are parsed and added as arguments for a "While" object should the first token
     * be "while." Finally, for the statement denoted by an "assign" token, an "Assignment" object
     * is returned with a String representing the id for the variable and an Expression representing
     * the value of the variable.
     * @throws scanner.ScanErrorException if the parseStatement encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     * @return a statement corresponding to the type of statement parsed.
     */
    public Statement parseStatement() throws scanner.ScanErrorException, java.io.IOException
    {
        if (currentToken.equals("display"))
        {
            eat("display");
            Expression exp = parseExpression();
            if (currentToken.equals("read"))
            {
                eat("read");
                String id = currentToken;
                eat(id);
                return new Display(exp, id);
            }
            return new Display(exp);
        }
        else if (currentToken.equals("assign"))
        {
            eat("assign");
            String id = currentToken;
            eat(id);
            eat("=");
            Expression num = parseExpression();
            Assignment assignment = new Assignment(id,num);
            return assignment;
        }
        else if (currentToken.equals("if"))
        {
            eat("if");
            Expression cond = parseExpression();
            eat("then");
            Program programthen = parseProgram();
            if (currentToken.equals("else"))
            {
                eat("else");
                Program programelse = parseProgram();
                eat("end");
                return new If(cond, programthen, programelse);
            }
            eat("end");
            return new If(cond, programthen);

        }
        else if (currentToken.equals("while"))
        {
            eat("while");
            Expression cond = parseExpression();
            eat("do");
            Program program = parseProgram();
            eat("end");
            return new While(cond,program);
        }

        throw new ScanErrorException("The expression " + currentToken + " is not valid in " +
                    "this language");

    }



    /**
     * As shown in the grammar above, there are three things that the parseValue method parses:
     * an id for a variable, a Number, or an expression surrounded by parentheses. For the first case,
     * a Variable object is returned with its argument as the id String. For the second case a newly
     * parsed number is returned; finally, the latter case simply involves removing the parenthesis
     * from the input stream and returning a newly parsed Expression.
     * @throws java.io.IOException if there is any other basic error
     * @postcondition the factor has been parsed and the currentToken now points to the token
     * after the current factor in the input stream
     * @return an Expression representing a Value from the grammar above
     */
    public Expression parseValue() throws scanner.ScanErrorException, java.io.IOException
    {
        if(currentToken.equals("("))
        {
            eat("(");
            Expression num =  parseExpression();
            eat(")");
            return num;
        }
        if(Scanner.isDigit(currentToken.charAt(0)))
        {
            Number num = parseNumber();
            return num;
        }
        if (Scanner.isLetter(currentToken.charAt(0)))
        {
            String id = currentToken;
            eat(currentToken);
            return new Variable(id);
        }
        throw new ScanErrorException("This factor is not supported by the language.");

    }

    /**
     * parseNegExpr handles for minus signs in Expressions, shown by the grammar above. If the current
     * token is a minus sign, a new BinOp object is returned with a multiplication operator,
     * a new number with an argument of -1, and a newly parsed Value. However, it there is no minus
     * sign, a newly parsed value is returned.
     * @return an Expression representing a NegExpr as shown above
     * @throws scanner.ScanErrorException if the parseStatement encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     */
    public Expression parseNegExpr() throws scanner.ScanErrorException, java.io.IOException
    {
        if(currentToken.equals("-"))
        {
            eat("-");
            Number n = new Number(-1);
            return new BinOp("*",n,parseValue());
        }
        return parseValue();
    }
    /**
     * parseMultExpr parses a MultExpr as shown in the above grammar, and handles multiplication and
     * division signs in expressions. First, a NegExpr is parsed and is set to a temporary Expression
     * variable. While the current token is equal to either the multiplication sign or the division
     * sign, this variable is set to a new BinOp object with the operator, the varaible, and a newly
     * parsed NegExpr as its arguments. This variable is then returned at the end of the method.
     * @return an expression representing a MultExpr
     * @throws scanner.ScanErrorException if the parseTerm encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     * @postcondition the term has been parsed and the currentToken now points to the token
     * after the current term in the input stream
     */
    public Expression parseMultExpr() throws scanner.ScanErrorException, java.io.IOException
    {
        Expression num = parseNegExpr();
        while(currentToken.equals("*")||currentToken.equals("/"))
        {
            if (currentToken.equals("*"))
            {
                eat("*");
                num = new BinOp("*",num,parseNegExpr());
            }
            else
            {
                eat("/");
                num = new BinOp("/",num,parseNegExpr());
            }
        }
        return num;

    }
    /**
     * pparseExpression parses a condition for an If or While statement or an AddExpr in the case
     * that no boolean operators are present, returning an Expression. At the very beginning, it
     * parses an AddExpr. Then, for each additional boolean operator(denoted by "relop" in the
     * grammar) in the current Expression, the operator and an Expression are parsed and added to
     * corresponding ArrayLists. If there are no boolean operators, the AddExpr object is just
     * returned. However, if there are boolean operators, a "Condition" object with the AddExpr
     * and two ArrayLists passed as parameters is returned.
     * @return a new Expression representing a Condition or an AddExpr
     * @throws scanner.ScanErrorException if the parseCondition encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     */
    public Expression parseExpression() throws ScanErrorException, java.io.IOException
    {
        Expression exp = parseAddExpr();
        ArrayList<String> relops = new ArrayList<String>();
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        while (currentToken.equals("=")||currentToken.equals("<>")||currentToken.equals(">") ||
                currentToken.equals("<")||currentToken.equals("<=")||currentToken.equals(">="))
        {
           relops.add(currentToken);
            eat(currentToken);
            expressions.add(parseAddExpr());
        }
        if (relops.size()==0)
        {
            return exp;
        }
        return new Condition(exp, relops, expressions);
    }
    /**
     * parseAddExpr parses an AddExpr as shown in the above grammar, handling expressions containing
     * plus and minus signs. It first parses a MultExpr and sets it to a temporary Expression
     * variable called num. Then, while the current token is either a plus or minus, num is set
     * to a new BinOp object with arguments of the corresponding operator, num, and a newly
     * parsed MultExpr. This num object is then returned at the end of the method.
     * @return an expression representing an AddExpr
     * @throws scanner.ScanErrorException if the parseExpression encounters an invalid statement
     * or expression
     * @throws java.io.IOException if there is any other basic error
     * @postcondition the expression has been parsed and the currentToken now points to the token
     * after the current expression in the input stream
     */
    public Expression parseAddExpr() throws scanner.ScanErrorException, java.io.IOException
    {
        Expression num = parseMultExpr();
        while(currentToken.equals("+")||currentToken.equals("-"))
        {
            if (currentToken.equals("+"))
            {
                eat("+");
                num = new BinOp("+",num,parseMultExpr());
            }
            else
            {
                eat("-");
                num = new BinOp("-",num,parseMultExpr());
            }
        }
        return num;

    }


    /**
     * The main method for the Parser class that helps to test the parser. A file is given as an
     * input stream to the scanner, which is taken as input stream to the parser. The parser's
     * parseProgram is then called to check to make sure the file is parsed correctly.
     * @param args The arguments of the main method
     * @throws FileNotFoundException if the given file is not found
     * @throws scanner.ScanErrorException if any method encounters an invalid statement or
     * expression
     * @throws java.io.IOException if there is any other basic error
     */
    public static void main(String[] args) throws FileNotFoundException,scanner.ScanErrorException,
            java.io.IOException
    {
        Parser parser = new Parser(new Scanner("input.txt"));
        parser.parseProgram().exec(new Environment());
    }
}
