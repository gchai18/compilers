package scanner;

import java.io.*;

/**
 * scanner.Scanner is a simple scanner that scans a file or string and
 * returns the lexemes in that file/string.
 *
 * @author Gabriel Chai
 * @version February 7, 2020
 * <p>
 * Usage:
 * <p>
 * nextToken() - to get the next token
 * hasNext() - to see if the Scanner has reached the end of the file/string
 * <p>
 * A period (.) marks the end of the file/string.
 * <p>
 * For files:
 * FileInputStream inStream = new FileInputStream(new File(<file name>);
 * scanner.Scanner lex = new scanner.Scanner(inStream);
 * <p>
 * For strings:
 * scanner.Scanner lex = new scanner.Scanner(input_string);
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * scanner.Scanner constructor for construction of a scanner that
     * uses an InputStream object for input.
     * Usage:
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * scanner.Scanner lex = new scanner.Scanner(inStream);
     *
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * scanner.Scanner constructor for constructing a scanner that
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: scanner.Scanner lex = new scanner.Scanner(input_string);
     *
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }


    /**
     * Reads the next character in the input stream.
     * If the character is a period, it is the end of the file/string.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if (inp == -1 || (char) inp == '.')
                eof = true;
            else
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * If currentChar matches its expected value, gets the next character.
     *
     * @param expected the expected value of currentChar
     * @throws ScanErrorException if currentChar does not match its expected value
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("Illegal character-expected " +
                    currentChar + " and found " + expected);
        }

    }

    /**
     * Checks to see if there are any more tokens in the input stream
     *
     * @return true if the InputStreamReader has not reached the end of the file; otherwise,
     * false
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * Skips any leading white space and comments and scans the next token of the input stream.
     * If no lexeme is found, then ends the execution.
     *
     * @return the lexeme found in the input stream.
     * If the input stream has reached the end of the file, outputs "END"
     * @throws ScanErrorException if currentChar does not match its expected value
     */
    public String nextToken() throws ScanErrorException
    {
        while (hasNext() && isWhiteSpace(currentChar))
        {
            eat(currentChar);
        }

        while (hasNext() && currentChar == '/')
        {
            eat(currentChar);
            if (currentChar == '/')
            {
                eat(currentChar);
                scanSingleLineComment();
            }
            else if (currentChar == '*')
            {
                eat(currentChar);
                scanMultiLineComment();
            }
            else if (currentChar == '=')
            {
                eat(currentChar);
                return "/=";
            }
            else
            {
                return "/";
            }

            while (hasNext() && isWhiteSpace(currentChar))
            {
                eat(currentChar);
            }
        }

        if (!hasNext())
        {
            return "END";
        }
        try
        {
            if (isDigit(currentChar))
            {
                return scanNumber();
            }
            if (isLetter(currentChar))
            {
                return scanIdentifier();
            }
            if (isOperand(currentChar))
            {
                return scanOperand();
            }
            throw new ScanErrorException("Unrecognized character in input stream");
        }
        catch (ScanErrorException e)
        {
            //eat(currentChar);
            System.exit(-1);
            return nextToken();
        }
    }

    /**
     * Checks to see if a character is a digit according to the regular expression digit:=[0-9]
     *
     * @param c the character to be checked
     * @return true if the character is a digit; otherwise,
     * false
     */
    public static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }

    /**
     * Checks to see if a character is a letter according to
     * the regular expression letter:=[a-z A-Z]
     *
     * @param c the character to be checked
     * @return true if the character is a letter; otherwise,
     * false
     */
    public static boolean isLetter(char c)
    {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * Checks to see if a character is a white space according to
     * the regular expression white space:= ['' '\t' '\r' '\n']
     *
     * @param c the character to be checked
     * @return true if the character is a white space; otherwise,
     * false
     */
    public static boolean isWhiteSpace(char c)
    {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    /**
     * Checks to see if a character is an operand or a special character
     *
     * @param c the character to be checked
     * @return true if the character is an operand; otherwise,
     * false
     */
    public static boolean isOperand(char c)
    {
        char[] operands = {'(', ')', '^', ';', '<', '>', ':', '+', '-', '*', '%', '=', ','};
        for (char o : operands)
        {
            if (c == o)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Scans the next number found in the input stream.
     * A number is defined by the regular expression number:=digit(digit)*
     *
     * @return the number found
     * @throws ScanErrorException if no number is found
     */
    private String scanNumber() throws ScanErrorException
    {
        String s = "";
        while (isDigit(currentChar))
        {
            s += currentChar;
            eat(currentChar);
        }
        if (s.length() == 0)
        {
            throw new ScanErrorException("No number found.");
        }
        return s;
    }

    /**
     * Scans the next identifier found in the input stream.
     * An identifier is defined by the regular expression identifier:= letter(letter|digit)*
     *
     * @return the identifier found
     * @throws ScanErrorException if there is no identifier found
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String s = "";
        if (isLetter(currentChar))
        {
            s += currentChar;
            eat(currentChar);
        }
        else
        {
            throw new ScanErrorException("No identifier found.");
        }

        while (isLetter(currentChar) || isDigit(currentChar))
        {
            s += currentChar;
            eat(currentChar);
        }
        return s;
    }

    /**
     * Scans the next operand or special character in the input stream
     *
     * @return the operand or special character found
     * @throws ScanErrorException if no operand or special character is found
     */
    private String scanOperand() throws ScanErrorException
    {
        String s = "";
        char[] operands = {'(', ')', '^', ';', ','};
        for (char c : operands)
        {
            if (c == currentChar)
            {
                s += currentChar;
                eat(currentChar);
                return s;
            }
        }

        char[] oper = {'<', '>', ':', '+', '-', '*', '%', '='};
        for (char c : oper)
        {
            if (c == currentChar)
            {
                s += currentChar;
                eat(currentChar);

                if (currentChar == '=')
                {
                    s += currentChar;
                    eat(currentChar);
                }
                else if (c == '<' && currentChar == '>')
                {
                    s += currentChar;
                    eat(currentChar);
                }

                return s;
            }
        }

        throw new ScanErrorException("No operand found.");
    }

    /**
     * Scans a single line comment found in the input stream
     *
     * @throws ScanErrorException if the expected value in the character in the input stream
     *                            does not match the actual value
     */
    private void scanSingleLineComment() throws ScanErrorException
    {
        while (hasNext() && currentChar != '\n')
        {
            eat(currentChar);
        }
        if (hasNext())
        {
            eat(currentChar);
        }
    }

    /**
     * Scans a multi line comment found in the input stream
     *
     * @throws ScanErrorException if the expected value in the character in the input stream
     *                            does not match the actual value
     */
    private void scanMultiLineComment() throws ScanErrorException
    {
        while (hasNext())
        {
            if (currentChar == '*')
            {
                eat(currentChar);
                if (currentChar == '/')
                {
                    eat(currentChar);
                    return;
                }
            }
            else
            {
                eat(currentChar);
            }
        }
    }
}
