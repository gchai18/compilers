package ast;

import emitter.*;
import environment.*;

/**
 * The abstract Statement class defines a statement that can execute itself
 * @author Gabriel Chai
 * @version April 17, 2020
 */
public abstract class Statement
{
    /**
     * Executes the statement
     * @param env the environment containing the variables and procedures used during execution
     */
    public abstract void exec(Environment env);

    /**
     * Emits a sequence of MIPS instructions for the corresponding AST component
     * @param e the Emitter to output the MIPS instructions
     */
    public void compile (Emitter e)
    {
        throw new RuntimeException("");
    }
}
