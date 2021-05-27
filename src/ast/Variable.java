package ast;

import emitter.Emitter;
import environment.*;

/**
 * The Variable class creates a variable with a given name
 *
 * @author Gabriel Chai
 * @version April 17, 2020
 */
public class Variable extends Expression
{
    private String name;

    /**
     * Constructor for objects of class Variable
     * @param name the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Gets the value associated with the variable
     * @param env the environment that contains the variables and procedures used during evaluation
     * @return the value of the variable
     */
    @Override
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }

    /**
     * Loads the variable into $v0
     * @param e the Emitter to output the MIPS instructions
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("# Sets $v0 to var" + name);
        e.emit("la $t0 var" + name);
        e.emit("lw $v0 ($t0)\n");
    }
}

