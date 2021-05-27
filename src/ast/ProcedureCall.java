package ast;

import emitter.Emitter;
import environment.Environment;
import java.util.*;

/**
 * The ProcedureCall class describes an expression that executes a procedure
 * with the associated name and arguments
 *
 * @author Gabriel Chai
 * @version April 27, 2020
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> args;

    /**
     * Constructor for objects of class ProcedureCall
     * @param name the name of the procedure
     * @param args the arguments associated with the procedure
     */
    public ProcedureCall(String name, List<Expression> args)
    {
        this.name = name;
        this.args = args;
    }

    /**
     * Evaluates a procedure call by getting the corresponding procedure,
     * evaluating the arguments of the procedure call, and executing the body of the procedure.
     * Creates a variable in the local environment with the name of the procedure
     * @param env the environment that contains the variables and procedures used during evaluation
     * @return the value of the variable associated with the procedure call
     */
    @Override
    public int eval(Environment env)
    {
        Environment loc = new Environment(env);
        loc.setVariable(name,0);
        ProcedureDeclaration dec = loc.getProcedure(name);
        List<String> params = dec.getParams();
        int len = params.size();
        for (int i=0; i<len; i++)
        {
            loc.declareVariable(params.get(i), args.get(i).eval(env));
        }
        dec.getStatement().exec(loc);
        return loc.getVariable(name);
    }

}
