package ast;

import java.util.*;

import emitter.Emitter;
import environment.*;

/**
 * The Block class contains List of statements that it can execute
 * @author Gabriel Chai
 * @version April 13, 2020
 */
public class Block extends Statement
{
    private List<Statement> stmts;

    /**
     * Constructor for objects of of Class Block
     * @param stmts a list of statements
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Evaluates all the statements in the Block
     * @param env the environment containing the variables and procedures used for evaluation
     */
    @Override
    public void exec(Environment env)
    {
        for(Statement stmt: stmts)
        {
            stmt.exec(env);
        }
    }

    /**
     * Compiles all statements in the block
     * @param e the Emitter to output the MIPS instructions
     */
    @Override
    public void compile(Emitter e)
    {
        for (Statement stmt: stmts)
        {
            stmt.compile(e);
        }
    }
}
