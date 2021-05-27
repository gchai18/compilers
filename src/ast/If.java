package ast;

import emitter.Emitter;
import environment.*;

/**
 * The If class defines a statement that is executed if a condition evaluates to true
 * @author Gabriel Chai
 * @version April 13, 2020
 */
public class If extends Statement
{
    private Condition cond;
    private Statement stmt;

    /**
     * Constructors for objects of class If.
     * @param cond the condition that determines whether the statement should be executed
     * @param stmt the statement that will be executed if the condition evaluates to true
     */
    public If (Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Checks to see if the condition is true. If the condition is true, executes the statement
     * @param env the environment containing the variables and procedures used during execution
     */
    @Override
    public void exec (Environment env)
    {
        if (cond.eval(env)==1)
        {
            stmt.exec(env);
        }
    }

    /**
     * Compiles the condition of the if statement, the statement in the if, and the end label
     * @param e the Emitter to output the MIPS instructions
     */
    @Override
    public void compile(Emitter e)
    {
        String label = "endif"+e.nextLabelID();
        cond.compile(e, label);
        stmt.compile(e);
        e.emit("");
        e.emit(label + ":");
    }
}
