package ast;

import emitter.Emitter;
import environment.*;

/**
 * The Writeln class defines a Statement that prints the value of an expression
 *
 * @author Gabriel Chai
 * @version April 17, 2020
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Constructor for objects of class Writeln
     * @param exp the expression to be evaluated and printed
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Evaluates the expression and prints its value
     * @param env the environment containing the variables and procedures used during execution
     */
    @Override
    public void exec (Environment env)
    {
        System.out.println(exp.eval(env));
    }

    /**
     * Compiles the expression, prints $v0, and then prints a new line
     * @param e the Emitter to output the MIPS instructions
     */
    @Override
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("# Prints $v0");
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall\n");
        e.emit("# Prints a new line");
        e.emit("la $a0 newline");
        e.emit("li $v0 4");
        e.emit("syscall\n");
    }
}
