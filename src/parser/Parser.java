package parser;

import ast.Number;
import scanner.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import ast.*;
import environment.*;

/**
 * parser.Parser is a parser that scans a file or string and
 * parses the statements in the file/string using an ast.
 *
 * The statements that the Parser parses follows the grammar:
 *
 * program -> VAR maybevars; program | programs
 * programs -> PROCEDURE id(maybeparms); stmt programs | stmt.
 * maybevars -> vars | epsilon
 * vars -> vars, id | vars
 * maybeparms -> parms | epsilon
 * parms -> parms, id | id
 * statement -> WRITELN(expression); | BEGIN whilebegin | id:=expression; |
 *              IF cond DO statement | WHILE cond DO statement
 * whilebegin -> END; | statement whilebegin
 * expression -> term whileexpression
 * whileexpression -> +term whilexpression | -term whileexpression | epsilon
 * term -> factor whileterm
 * whileterm -> *factor whileterm | /factor whileterm | epsilon
 * factor -> (expression) | -factor | number | id (maybeargs) | id
 * maybeargs -> args | epsilon
 * args-> args, expr | expr
 * cond -> expression relop expression
 * relop -> > | < | = | <> | <= | >=
 *
 * @author Gabriel Chai
 * @version March 17, 2020
 *
 * Usage:
 *
 * parseStatement() - to parse the contents of a file or string
 *
 * A period (.) marks the end of the file/string.
 *
 * For files:
 * FileInputStream inStream = new FileInputStream(new File(<file name>);
 * scanner.Scanner lex = new scanner.Scanner(inStream);
 * parser.Parser p = new parser.Parser(lex);
 *
 * For strings:
 * scanner.Scanner lex = new scanner.Scanner(input_string);
 * parser.Parser p = new parser.Parser(lex);
 */
public class Parser
{
    private Scanner sc;
    private String currentToken;

    /**
     * parser.Parser constructor for constructing a Parser that uses a scanner
     * @param sc the Scanner for a file/string to be parsed by the parser
     * @throws ScanErrorException if the expected value in the character in the input stream
     *                            does not match the actual value
     */
    public Parser (Scanner sc) throws ScanErrorException
    {
        this.sc = sc;
        currentToken = sc.nextToken();
    }

    /**
     * If currentToken matches its expected value, gets the next token.
     * If the currentToken does not match its expected value, ends the execution
     *
     * @param token the expected value of the current token
     */
    private void eat(String token)
    {
        try
        {
            if (token.equals(currentToken))
            {
                currentToken = sc.nextToken();
            }
            else
            {
                throw new IllegalArgumentException("Expected: "+currentToken+" Found: "+token);
            }
        }
        catch (ScanErrorException e)
        {
            System.exit(-1);
        }
    }

    /**
     * Parses the next number in the input stream.
     * @precondition current token is an integer
     * @postcondition number token has been eaten
     * @return the value of the parsed integer
     */
    private Number parseNumber()
    {
        int num = Integer.parseInt(currentToken);
        eat(currentToken);
        return new Number(num);
    }

    /**
     * Parses a condition, which follows the production
     * condition -> expression relop expression
     * relop -> > | < | = | <> | <= | >=
     * @return the condition that is parsed
     */
    public Condition parseCondition()
    {
        Expression exp1 = parseExpression();
        String relop = currentToken;
        eat(currentToken);
        Expression exp2 = parseExpression();
        return new Condition(exp1, relop, exp2);
    }

    /**
     * Parses a program, which follows the production
     * program -> VAR maybevars; program | programs
     * programs -> PROCEDURE id(maybeparms); stmt programs | stmt.
     * maybevars -> vars | epsilon
     * vars -> vars, id | vars
     * maybeparms -> parms | epsilon
     * parms -> parms, id | id
     * @return the Program that is parsed
     */
    public Program parseProgram()
    {
        List<String> variables = new ArrayList<String>();
        while (currentToken.equals("VAR"))
        {
            eat("VAR");
            while(!currentToken.equals(";"))
            {
                variables.add(currentToken);
                eat(currentToken);
                if (currentToken.equals(","))
                {
                    eat(",");
                }
            }
            eat(";");
        }
        List<ProcedureDeclaration> procedures = new ArrayList<ProcedureDeclaration>();
        while(currentToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String id = currentToken;
            eat(id);
            List<String> parms = new ArrayList<String>();
            eat("(");
            while(!currentToken.equals(")"))
            {
                parms.add(currentToken);
                eat(currentToken);
                if (currentToken.equals(","))
                {
                    eat(",");
                }
            }
            eat(")");
            eat(";");
            procedures.add(new ProcedureDeclaration(id, parms, parseStatement()));
        }
        return new Program(variables, procedures, parseStatement());
    }

    /**
     * Parses a statement, which follows the production
     * statement->WRITELN(expression); | BEGIN whilebegin | id:=expression; |
     *            IF cond THEN statement | WHILE cond DO statement
     *
     * @return the statement that is parsed
     */
    public Statement parseStatement()
    {
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            Statement w =  new Writeln(exp);
            return w;
        }
        else if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            List<Statement> statements = new ArrayList<Statement>();
            parseWhileBegin(statements);
            return new Block(statements);
        }
        else if (currentToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement stmt = parseStatement();
            return new If(cond, stmt);
        }
        else if (currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement stmt = parseStatement();
            Statement whil = new While(cond, stmt);
            return whil;
        }
        else
        {
            String var = currentToken;
            eat(var);
            eat(":=");
            Statement assign = new Assignment(var, parseExpression());
            eat(";");
            return assign;
        }
    }

    /**
     * Parsers whilebegin, which follows the production
     * whilebegin -> END; | statement whilebegin
     *
     * @param statements the list of statements between BEGIN and END
     */
    private void parseWhileBegin(List<Statement> statements)
    {
        if (currentToken.equals("END"))
        {
            eat("END");
            eat(";");
        }
        else
        {
            statements.add(parseStatement());
            parseWhileBegin(statements);
        }
    }

    /**
     * Parses a factor, which follows the production
     * factor -> (expression) | -factor | number | id (maybeargs) | id
     * maybeargs -> args | epsilon
     * args-> args, expr | expr
     * @return the value of the factor
     */
    private Expression parseFactor()
    {
        if (currentToken.equals("("))
        {
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            return exp;
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            return new BinOp("-", new Number(0), parseFactor());
        }
        else if (Scanner.isLetter(currentToken.charAt(0)))
        {
            String id = currentToken;
            eat(id);
            if (currentToken.equals("("))
            {
                eat("(");
                List<Expression> args = new ArrayList<Expression>();
                while(!currentToken.equals(")"))
                {
                    args.add(parseExpression());
                    if (currentToken.equals(","))
                    {
                        eat(",");
                    }
                }
                eat(")");
                return new ProcedureCall(id, args);
            }
            else
            {
                return new Variable(id);
            }
        }
        else
        {
            return parseNumber();
        }
    }

    /**
     * Parses a term, which follows the production
     * term -> factor whileterm
     * @return the value of the term
     */
    private Expression parseTerm()
    {
        Expression factor = parseFactor();
        return parseWhileTerm(factor);
    }

    /**
     * Parses a whileterm, which follows the production
     * whileterm -> *factor whileterm | /factor whileterm | epsilon
     * @param factor the factor that precedes the whileexpression
     * @return the value of the whileterm
     */
    private Expression parseWhileTerm(Expression factor)
    {
        if (currentToken.equals("*"))
        {
            eat("*");
            Expression exp = new BinOp("*", factor, parseFactor());
            return parseWhileTerm(exp);
        }
        else if (currentToken.equals("/"))
        {
            eat("/");
            Expression exp = new BinOp("/", factor, parseFactor());
            return parseWhileTerm(exp);
        }
        return factor;
    }

    /**
     * Parses an expression, which follows the production
     * expression -> term whileexpression
     * @return the value of the expression
     */
    private Expression parseExpression()
    {
        Expression term = parseTerm();
        return parseWhileExpression(term);
    }

    /**
     * Parses a whileexpression, which follows the production
     * whileexpression -> +term whilexpression | -term whileexpression | epsilon
     * @param term the term that precedes the whileexpression
     * @return the value of the whileexpression
     */
    private Expression parseWhileExpression(Expression term)
    {
        if (currentToken.equals("+"))
        {
            eat("+");
            Expression exp = new BinOp("+", term, parseTerm());
            return parseWhileExpression(exp);
        }
        else if (currentToken.equals("-"))
        {
            eat("-");
            Expression exp = new BinOp("-", term, parseTerm());
            return parseWhileExpression(exp);
        }
        return term;
    }

    /**
     * Creates a parser.Parser, which uses a scanner.Scanner that reads a file/string,
     * to parse the statements in the file/string using an ast
     * @param args information from the command line
     * @throws IOException if the file being scanned does not exist
     * @throws ScanErrorException if the expected value in the character in the input stream
     *                            does not match the actual value
     */
    public static void main (String[] args) throws IOException, ScanErrorException
    {
        Scanner sc = new Scanner(new FileInputStream(new File ("codegenTest.txt")));
        Parser p = new Parser(sc);
        Environment env = new Environment();
        while(sc.hasNext())
        {
            p.parseProgram().compile("codegen.asm");
        }
    }
}
