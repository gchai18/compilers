package ast;

import emitter.*;
import environment.*;

/**
 * The Number class contains an integer value
 * @author Gabriel Chai
 * @version April 13, 2020
 */
public class Number extends Expression
{
    private int value;

    /**
     * Constructor for objects of class number
     * @param value the value of the number
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Gives the value of the Number object
     * @param env the environment that contains the variables and procedures used during evaluation
     * @return the value of the Number object
     */
    @Override
    public int eval(Environment env)
    {
        return value;
    }

    /**
     * Sets $v0 to the number
     * @param e the Emitter to output the MIPS instructions
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("# Sets $v0 to " + value);
        e.emit("li $v0 " + value + "\n");
    }
}
