package ast;

import environment.Environment;

import java.util.*;

import emitter.*;

/**
 * The Program class stores the variables, procedures and statement of a program and executes them.
 *
 * @author Gabriel Chai
 * @version May 1, 2020
 */
public class Program extends Statement
{
    private static List<ProcedureDeclaration> procedures;
    private static List<String> variables;
    private static Statement stmt;

    /**
     * Constructor for object of class Program
     * @param variables the variables in the program
     * @param procedures the procedures in the program
     * @param stmt the statement after the procedures in a program
     */
    public Program(List<String> variables, List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.variables = variables;
        this.stmt = stmt;
    }

    /**
     * Declares teh variables and executes the procedures and statement in the program
     * @param env the environment containing the variables and procedures used during execution
     */
    @Override
    public void exec(Environment env)
    {
        for (String variable: variables)
        {
            env.declareVariable(variable, 0);
        }
        for (ProcedureDeclaration procedure: procedures)
        {
            procedure.exec(env);
        }
        stmt.exec(env);
    }

    /**
     * Uses an Emitter to write MIPS code for the program to a file
     * Compiles the statement and emits the variables
     * @param output the name of the output file
     */
    public void compile (String output)
    {
        Emitter e = new Emitter(output);
        e.emit("# @author Gabriel Chai");
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        stmt.compile(e);
        e.emit("li $v0 10");
        e.emit("syscall #halt");
        e.emit(".data");
        e.emit("newline: .asciiz \"\\n\" ");
        for (String variable: variables)
        {
            e.emit("var" + variable + ": .word 0");
        }
        e.close();
    }
}
