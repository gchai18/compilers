package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The While class defines a statement that will be executed while a condition evaluates to true
 * @author Gabriel Chai
 * @version April 13, 2020
 */
public class While extends Statement
{
    private Condition cond;
    private Statement stmt;

    /**
     * Constructors for objects of class While
     * @param cond the condition that is evaluated to determine whether that statement is executed
     * @param stmt the statement that is executed if the condition evaluates to true
     */
    public While (Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * Executes the statement while the condition evaluates to true
     * @param env the environment containing the variables and procedures used during execution
     */
    @Override
    public void exec (Environment env)
    {
        while (cond.eval(env)==1)
        {
            stmt.exec(env);
        }
    }

    /**
     * Emits the begin label of the loop, compiles the condition, compiles the statement,
     * jumps to the begin label, and emits the end label
     * @param e the Emitter to output the MIPS instructions
     */
    @Override
    public void compile(Emitter e)
    {
        int labelID = e.nextLabelID();
        String beginLabel = "beginwhile"+labelID;
        String endLabel = "endwhile"+labelID;
        e.emit(beginLabel + ":");
        cond.compile(e, endLabel);
        stmt.compile(e);
        e.emit("j " + beginLabel + "\n");
        e.emit(endLabel + ":");

    }
}
