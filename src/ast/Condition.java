package ast;

import emitter.Emitter;
import environment.*;

/**
 * The Condition class defines an expression that compares two expressions with a relative operator
 * and evaluates to true or false
 * @author Gabriel Chai
 * @version April 13, 2020
 */
public class Condition extends Expression
{
    private Expression exp1;
    private String relop;
    private Expression exp2;

    /**
     * Constructor for objects of class Condition
     * @param exp1 an expression to be compared
     * @param relop a String representing the relative operator used to compare the two expressions
     * @param exp2 an expression to be compared
     */
    public Condition(Expression exp1, String relop, Expression exp2)
    {
        this.exp1 = exp1;
        this.relop = relop;
        this.exp2 = exp2;
    }

    /**
     * Compares the expressions with the relative operator
     * @param env the environment containing the variables and procedures used for evaluation
     * @return 1 if the condition evaluates to true, 0 if the condition evaluates to false
     */
    @Override
    public int eval(Environment env)
    {
        int value1 = exp1.eval(env);
        int value2 = exp2.eval(env);
        boolean bool = true;
        if (relop.equals("="))
        {
            bool = (value1==value2);
        }
        else if (relop.equals("<>"))
        {
            bool = (value1!=value2);
        }
        else if (relop.equals("<"))
        {
            bool = (value1<value2);
        }
        else if (relop.equals(">"))
        {
            bool = (value1>=value2);
        }
        else if (relop.equals("<="))
        {
            bool = (value1<=value2);
        }
        else if (relop.equals(">="))
        {
            bool = (value1>=value2);
        }

        if (bool) return 1;
        return 0;
    }

    /**
     * Compiles the first expression and puts the register onto a stack
     * compiles the second expression, and then compares the two values using the relative operator
     * checks to see if the initial condition is true by checking the opposite condition
     * jumps to the target label if the initial condition results in false
     * @param e the Emitter to output the MIPS instructions
     * @param label the label to jump to if the initial condition results in false
     */
    public void compile(Emitter e, String label)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");

        if (relop.equals("="))
        {
            e.emit("bne $t0 $v0 " + label);
        }
        else if (relop.equals("<>"))
        {
            e.emit("beq $t0 $v0 " + label);
        }
        else if (relop.equals("<"))
        {
            e.emit("bge $t0 $v0 " + label);
        }
        else if (relop.equals(">"))
        {
            e.emit("ble $t0 $v0 " + label);
        }
        else if (relop.equals("<="))
        {
            e.emit("bgt $t0 $v0 " + label);
        }
        else if (relop.equals(">="))
        {
            e.emit("blt $t0 $v0 " + label);
        }
        e.emit("");


    }
}
