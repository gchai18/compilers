package ast;

import environment.*;
import emitter.*;

/**
 * The abstract Expression class defines an expression that evaluates to an integer
 * @author Gabriel Chai
 * @version April 7, 2020
 */
public abstract class Expression
{
    /**
     * Evaluates the expression
     * @param env the environment that contains the variables and procedures used during evaluation
     * @return the value of the expression
     */
    public abstract int eval(Environment env);

    /**
     * Emits a sequence of MIPS instructions for the corresponding AST component
     * @param e the Emitter to output the MIPS instructions
     */
    public void compile (Emitter e)
    {
        throw new RuntimeException("");
    }
}
