package environment;
import ast.ProcedureDeclaration;

import java.util.*;

/**
 * The Environment class sets variables to values and stores the variables and
 * sets procedures to a given ProcedureDeclaration and stores them.
 *
 * @author Gabriel Chai
 * @version April 13, 2020
 */
public class Environment
{
    private Map<String, Integer> variables;
    private Map<String, ProcedureDeclaration> procedures;
    private Environment parent;

    /**
     * Constructor for the global environment, which has no parent.
     * Initializes a HashMap to store the variables and their values and
     * a HashMap to store the procedures and their declarations.
     */
    public Environment ()
    {
        variables = new HashMap<String, Integer>();
        procedures = new HashMap<String, ProcedureDeclaration>();
        parent = null;
    }

    /**
     * Constructor for local environments with a parent.
     * Initializes a HashMap to store the variables and their values and
     * a HashMap to store the procedures and their declarations.
     *
     * @param parent the parent environment
     */
    public Environment (Environment parent)
    {
        variables = new HashMap<String, Integer>();
        procedures = new HashMap<String, ProcedureDeclaration>();
        this.parent = parent;
    }

    /**
     * Gets the map of variables stores in the environment
     * @return the variables in the environment
     */
    public Map<String, Integer> getVariables()
    {
        return variables;
    }

    /**
     * Sets a variable to a given value and stores the variable
     * @param variable the name of the variable
     * @param value the value of the variable
     */
    public void setVariable (String variable, int value)
    {
        if (!variables.containsKey(variable)&&parent!=null&&
                parent.getVariables().containsKey(variable))
            parent.setVariable(variable, value);
        else
            variables.put(variable, value);
    }

    /**
     * Sets a variable to a given value and stores the variable
     * @param variable the name of the variable
     * @param value the value of the variable
     */
    public void declareVariable (String variable, int value)
    {
        variables.put(variable, value);
    }

    /**
     * Gets the value of a variable. If the environment does not contain the variable,
     * gets the variable from its parent environment.
     * @param variable the name of the variable
     * @return the value of the variable
     */
    public int getVariable (String variable)
    {
        if (variables.containsKey(variable))
        {
            return variables.get(variable);
        }
        return parent.getVariable(variable);
    }

    /**
     * Sets a procedure to a given ProcedureDeclaration and stores the procedure.
     * If it is a local environment, stores it in the parent environment.
     * @param name the name of the procedure
     * @param dec the ProcedureDeclaration
     */
    public void setProcedure (String name, ProcedureDeclaration dec)
    {
        if (parent!=null)
        {
            parent.setProcedure(name, dec);
        }
        else
        {
            procedures.put(name, dec);
        }
    }

    /**
     * Gets the ProcedureDeclaration associated with an id
     * @param name the name of the procedure
     * @return the corresponding ProcedureDeclaration
     */
    public ProcedureDeclaration getProcedure (String name)
    {
        if (parent!=null)
        {
            return parent.getProcedure(name);
        }
        return procedures.get(name);
    }
}
