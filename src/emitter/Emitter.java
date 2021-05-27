package emitter;

import java.io.*;

/**
 * The Emitter class outputs code to a file one line at a time
 *
 * @author Gabriel Chai
 * @author Anu Datar
 * @version May 14, 2020
 */
public class Emitter
{
    private PrintWriter out;
    private int labelID;

    /**
     * Constructor for objects of class Emitter
     * creates an emitter for writing to a new file with given name
     * @param outputFileName the name of the output file
     */
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
            labelID = 0;
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * prints one line of code to file (with non-labels indented)
     * @param code the piece of code to be printed
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    /**
     * closes the file.  should be called after all calls to emit.
     */
    public void close()
    {
        out.close();
    }

    /**
     * Prints the MIPS assembly code for pushing a register onto a stack
     * @param reg the register to be pushed onto the stack
     */
    public void emitPush(String reg)
    {
        emit("subu $sp $sp 4");
        emit("sw " +reg +" ($sp)\n");
    }

    /**
     * Prints the MIPS assembly code for popping a register from a stack
     * @param reg the register to be popped from a stack
     */
    public void emitPop(String reg)
    {
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4\n");
    }

    /**
     * Generates the next label id for if statements and while loops.
     * @return the next label id
     */
    public int nextLabelID()
    {
        labelID++;
        return labelID;
    }
}