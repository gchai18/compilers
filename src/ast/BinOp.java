package ast;

import emitter.Emitter;
import environment.*;

/**
 * The BinOp class defines an expression that performs a binary operation on two expressions
 *
 * @author Gabriel Chai
 * @version April 17, 2020
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructor for objects of class BinOp
     * @param op a String containing the binary operator to be used
     * @param exp1 an expression for the the binary operation
     * @param exp2 the other expression for the binary operation
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Performs a binary operation on two expressions
     * @param env the environment containing the variables and procedures to be used for evaluation
     * @return the result of the binary operation on the two expressions
     */
    @Override
    public int eval(Environment env)
    {
        if (op.equals("+"))
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if (op.equals("-"))
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else if (op.equals("*"))
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        else if (op.equals("/"))
        {
            return exp1.eval(env) / exp2.eval(env);
        }
        return 0;
    }

    /**
     * Compiles the first expression and puts the register onto a stack
     * compiles the second expression, and performs the operation on the two values
     * @param e the Emitter to output the MIPS instructions
     */
    @Override
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");

        if (op.equals("+"))
        {
            e.emit("# Adds $t0 and $v0");
            e.emit("addu $v0 $t0 $v0\n");
        }
        else if (op.equals("-"))
        {
            e.emit("# Subtracts $v0 from $t0");
            e.emit("subu $v0 $t0 $v0\n");
        }
        else if (op.equals("*"))
        {
            e.emit("# Multiplies and $v0");
            e.emit("mulu $v0 $t0 $v0\n");
        }
        else if (op.equals("/"))
        {
            e.emit("# Divides $t0 by $v0");
            e.emit("divu $v0 $t0 $v0\n");
        }
    }
}
