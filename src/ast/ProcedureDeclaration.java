package ast;

import emitter.Emitter;
import environment.Environment;
import java.util.*;

/**
 * The ProcedureDeclaration stores a procedure that is declared
 * in the beginning of the program into the environment.
 * @author Gabriel Chai
 * @version April 27, 2020
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement stmt;
    private List<String> params;

    /**
     * Constructor for objects of class ProcedureDeclaration
     * @param name the name of the procedure
     * @param params the parameters for the procedure
     * @param stmt the body of the procedure
     */
    public ProcedureDeclaration (String name, List<String> params, Statement stmt)
    {
        this.name = name;
        this.params = params;
        this.stmt = stmt;
    }

    /**
     * Gets the parameters associated with the procedure
     * @return the list of parameters
     */
    public List<String> getParams()
    {
        return params;
    }

    /**
     * Gets the body of the procedure
     * @return the Statement associated with the procedure
     */
    public Statement getStatement()
    {
        return stmt;
    }

    /**
     * Stores the procedure into the environment
     * @param env the environment containing the variables and procedures used during execution
     */
    @Override
    public void exec(Environment env)
    {
        env.setProcedure(name, this);
    }

}
