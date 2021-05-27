package ast;

import emitter.Emitter;
import environment.*;

/**
 * The Assignment class defines a statement that assigns an expression to a variable
 * @author Gabriel Chai
 * @version April 13, 2020
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;

    /**
     * Constructor for objects of class Assignment
     * @param var the name of the variable
     * @param exp the expression that the variable is equal to
     */
    public Assignment(String var, Expression exp)
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Evaluates an expression and assigns the value of that expression to the variable
     * @param env the environment containing the variables and procedures used during execution
     */
    @Override
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }

    /**
     * Compiles the expression that the variable is being assigned to
     * Stores the value of the expression into the variable
     * @param e the Emitter to output the MIPS instructions
     */
    @Override
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("# Assigns $v0 to var" + var);
        e.emit("la $t0 var" + var);
        e.emit("sw $v0 ($t0)\n");
    }
}
