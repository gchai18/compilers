package scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ScannerTester is a tester for the scanner.Scanner class
 *
 * @author Gabriel Chai
 * @version February 6, 2020
 */
public class ScannerTester
{
    /**
     * Creates a scanner.Scanner that reads from a file and
     * prints out all the tokens in that file, separated by a line
     * @param args Information from the command line
     * @throws IOException if the file being scanned does not exist
     * @throws ScanErrorException if the expected value in the character in the input stream
     *                            does not match the actual value
     */
    public static void main (String[] args) throws IOException, ScanErrorException
    {
        Scanner sc = new Scanner(new FileInputStream(new File( "ScannerTest.txt")));
        while(sc.hasNext())
        {
            System.out.println(sc.nextToken());
        }
    }
}
