package scanner;

import java.io.*;

/**
 * scanner.Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1
 * @author Sahil Jain
 * @version 1/29/18
 *  
 * Usage:
 * The scanner.Scanner class analyzes the given input character by character and outputs a list of lexemes
 * contained within than input file. Comments are removed, so nothing within comment bounds is
 * included in the lexeme list. In addition, no white spaces(including \t, \n, \r, and spaces)
 * are included in the lexeme list.
 * Lexemes are defined as the following:
 * 1. A number, defined as a digit followed by a set of 0 or more digits.
 * 2. An identifier, defined as a letter followed by any combination of digits or letters.
 * 3. An operator(Exceptions include :=, >=, and <=)
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * scanner.Scanner constructor for constructing a scanner that
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: scanner.Scanner lex = new scanner.Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString) throws java.io.IOException
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(inString,
                    true));
            out.write("\n");
            out.write(".");
            out.close();
        }
        catch (IOException e)
        {
            //exception handling left as an exercise for the reader
        }
        in = new BufferedReader(new FileReader(inString));
        eof = false;
        getNextChar();
    }
    /**
     * Method: getNextChar
     * Sets the next character in the file to be currentchar, sets the eof variable to true if
     * the end of the file has been reached, and handles the IOException.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1)
                eof = true;
            else
                currentChar = (char) inp;
            if (currentChar=='.')
                eof = true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    /**
     * Method: eat
     * Using the getNextChar() method, the eat method sets the currentChar to be the next character
     * in the file if the given character is equal to the current character
     * @throws ScanErrorException if the current character does not equal to the given character
     * @param expected the character that the current character should be compared to
     */
    
    private void eat(char expected) throws ScanErrorException
    {
        if(expected==currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("the char equals " + expected + "and it should equal "
                    +currentChar);

        }

    }
    /**
     * Method: hasNext returns true if the end of the file has not been reached
     * @return true if the end of the file has not been reached, false otherwise
     */
    public boolean hasNext()
    {
        return !eof;
    }
    /**
     * Method: nextToken gets the next token/lexeme in the input file
     * @return a string representing the next token/lexeme in the input file
     * @throws ScanErrorException if an unidentified symbol is in the input file
     * @throws IOException if there is trouble reading the file (handled in the getNextChar()
     * method)
     */
    public String nextToken() throws ScanErrorException, java.io.IOException
    {
        if (!hasNext())
        {
            return "END";
        }
        else
        {
            while(isWhiteSpace(currentChar))
            {
                eat(currentChar);
                if (!hasNext())
                {
                    return "END";
                }
            }
            String next;
            if (isLetter(currentChar))
            {
                next = scanIdentifier();
            }
            else if(isOperator(currentChar))
            {
                next = scanOperator();
            }
            else if (isDigit(currentChar))
            {
                next = scanNumber();
            }
            else
            {
                throw new ScanErrorException("Expected letter, operator, or digit, but found " +
                        currentChar + " instead");
            }
            return next;
        }
        
    }
    /**
     *@return true if the char is a digit
     *@param s the char to be checked
     */
    public static boolean isDigit(char s)
    {
        return s>='0'&&s<='9';
    }
    /**
     *@return true if the char is a letter
     *@param s the char to be checked
     */
    public static boolean isLetter(char s)
    {
        return (s>='a'&&s<='z')||(s>='A'&&s<='Z');
    }

    /**
     * @param s the char to be checked
     * @return true if the char is an operator
     */
    public static boolean isOperator(char s)
    {
        return s=='='||s=='+'||s=='-'||s=='*'||s=='/'||s=='%'||s=='('||s==')'||s==';'||s==':'
                ||s=='>'||s=='<'||s=='{'||s=='}'||s==',';
    }
    /**
     * @return true if the char is a white space
     *@param s the char to be checked
     */
    public static boolean isWhiteSpace(char s)
    {
        return s==' '||s=='\t'||s=='\r'||s=='\n';
    }

    /**
     * Scans a number token from the input file
     * @precondition the current character is a digit
     * @return a String representing the next number in the input file
     * @throws ScanErrorException if there is a letter or unknown symbol found while parsing number
     */
    private String scanNumber()throws ScanErrorException
    {
        if (!isDigit(currentChar))
        {
            throw new ScanErrorException("The current char should be a digit but is" + currentChar
                    + " instead.");
        }
        String s = "";
        while(!isWhiteSpace(currentChar))
        {
            if (!isDigit(currentChar)&&!isOperator(currentChar))
            {
                throw new ScanErrorException("The current char should be a digit or operator but " +
                        "is " + currentChar + " instead.");
            }
            else if (isOperator(currentChar))
            {
                return s;
            }
            s+=currentChar;
            eat(currentChar);

        }
        return s;
    }
    /**
     * Scans the next operator in the input file and removes comments in the input file
     * @precondition the current character is an operator
     * @return a String representing the next operator in the input file or "" if removing a comment
     * @throws ScanErrorException if an unidentified symbol is present
     * @throws java.io.IOException if there is trouble reading the file (handled in getNextChar)
     */
    private String scanOperator() throws ScanErrorException, java.io.IOException
    {
        char save = currentChar;
        eat(currentChar);
        if (save == '/' && currentChar == '/')
        {
            in.readLine();
            eat(currentChar);
            return "";
        }
        else if (save == '/' && currentChar == '*')
        {
            eat(currentChar);
            while (save != '*' || currentChar != '/')
            {
                save = currentChar;
                eat(currentChar);
            }
            eat(currentChar);
            return "";
        }
        else if((save ==':'||save=='>'||save=='<')&&currentChar=='=')
        {
            char newsave = currentChar;
            eat(currentChar);
            return "" + save+newsave;
        }
        else if (save=='<'&&currentChar=='>')
        {
            char newsave = currentChar;
            eat(currentChar);
            return "" + save + newsave;
        }
        else if ((save == '(' && currentChar =='*')||(save=='*'&&currentChar==')'))
        {
            char newsave = currentChar;
            eat(currentChar);
            return "" + save+newsave;
        }
        else
        {
            return "" + save;
        }
    }

    /**
     * Scans the next identifier in the input file.
     * @return a String representing the next identifier in the input file
     * @throws ScanErrorException if an unidentified symbol is found
     */
    private String scanIdentifier()throws ScanErrorException
    {
        if (!isLetter(currentChar))
        {
            throw new ScanErrorException("The current char should be a letter but is" +
                    currentChar + " instead.");
        }
        String s = "";
        //System.out.println(s);
        while(!isWhiteSpace(currentChar))
        {
            if (!isDigit(currentChar)&&!isLetter(currentChar)&&!isOperator(currentChar))
            {
                throw new ScanErrorException("The current char should be a letter, operator, or " +
                        "digit but is" + currentChar + " instead.");
            }
            if (isOperator(currentChar))
            {
                return s;
            }
            s+=currentChar;
            eat(currentChar);

        }
        return s;
    }

    /**
     * The main method of the scanner.Scanner class that helps to test the scanner.Scanner. A new scanner.Scanner is created
     *  with the given input file and all tokens in that file are printed out to the console.
     * @param args The arguments of the main method.
     * @throws FileNotFoundException if the given file does not exist or cannot be found
     * @throws ScanErrorException if an unidentified character appears in the file
     * @throws java.io.IOException if there is trouble reading the file
     */
    public static void main(String[] args)throws FileNotFoundException, ScanErrorException,
            java.io.IOException
    {
        Scanner scanner = new Scanner("input.txt");
        while (scanner.hasNext())
        {
            System.out.println(scanner.nextToken());
        }

    }
}
