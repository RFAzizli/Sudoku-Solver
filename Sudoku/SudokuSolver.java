package Sudoku;
import java.io.*;
public class SudokuSolver {
    public static void main(String[] args)throws Exception{
        // Create a new Field instance
        Field f=new Field();
        // Read from sample file into and fill the field
        f.readFrom(new File("Sample1.txt"));
        // Solver instance, requires a Field instance that you want to solve
        Solver s=new Solver(f);
        // Field before
        System.out.println(f);
        // Start solving
        System.out.println(s.solve(0));
        // Field after solving, will be the same if the sudoku has no solution
        System.out.println(f);
    }
}
