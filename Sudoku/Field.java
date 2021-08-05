package Sudoku;

/*
    Imports required for the Class to work
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Field {

    public static final int dim=9;
    public static final int subDim=3;
    /*
        values[][] - array that stores all the values on the field
     */
    private int[][] values;
    /*
        isFixed[][] - when the sudoku field is read from file, all of the numbers that are not equal to zero,
        i.e. empty cells, will be fixed, so the player can't remove them or place over them.
     */
    private boolean[][] isFixed;
    /*
        subGridHasValue[][][] - stores information about a subgrid containing a specific value.
        For example, you want to check if the first subgrid on the first row contains number 7. subGridHasValue[0][0][7-1] returns corresponding boolean.
        You want to check the second subgrid on the third row contains number 4, subGridHasValue[2][1][4-1]
        Why 7-1 or 4-1, since array indexing starts with 0, but we work only with values from 1-9 we have to subtract 1 from every value.
     */
    private boolean[][][] subGridHasValue;
    /*
        rowHasValue[][]- stores information about a row containing a specific value.
        For example, let's say you want to check if row 0 contains value 2, rowHasValue[0][2-1] will have the corresponding boolean.
        Why 2-1, since array indexing starts with 0, but we work only with values from 1-9 we have to subtract 1 from every value.
     */
    private boolean[][] rowHasValue;
    /*
        colHasValue[][]- stores information about a col containing a specific value.
        For example, let's say you want to check if col 0 contains value 2, colHasValue[0][2-1] will have the corresponding boolean.
        Why 2-1, since array indexing starts with 0, but we work only with values from 1-9 we have to subtract 1 from every value.
     */
    private boolean[][] colHasValue;

    /*
        Field() - initializes all the local variables
     */
    public Field(){
        this.values=new int[this.dim][this.dim];
        this.isFixed=new boolean[this.dim][this.dim];
        this.subGridHasValue=new boolean[this.subDim][this.subDim][this.dim];
        this.rowHasValue=new boolean[this.dim][this.dim];
        this.colHasValue=new boolean[this.dim][this.dim];
    }
    /*
        place() - wrapper method for placeVal()
     */
    public boolean place(int val,int row,int col){
        if((row<0 || row>8) || (col<0 || col>8) || (val<0 || val>9)){
            throw new IllegalArgumentException();
        }
        return this.placeVal(val,row,col);
    }
    /*
        placeVal(int val,int row,int col) - tries to place a value val at Row row and Column col,
        if possible returns true, otherwise returns false.
     */
    private boolean placeVal(int val,int row, int col){
        if(val==0){
            return true;
        }
        if(this.isFixed[row][col]==true){
            return false;
        }
        if(this.subGridHasValue[row/3][col/3][val-1]==true){
            return false;
        }
        if(this.rowHasValue[row][val-1]==true){
            return false;
        }
        if(this.colHasValue[col][val-1]==true){
            return false;
        }
        this.values[row][col]=val;
        this.subGridHasValue[row/3][col/3][val-1]=true;
        this.rowHasValue[row][val-1]=true;
        this.colHasValue[col][val-1]=true;
        return true;
    }
    /*
        remove() - wrapper method for removeVal()
     */
    public boolean remove(int row,int col){
        if((row<0 || row>8) || (col<0 || col>8)){
            throw new IllegalArgumentException();
        }
        return this.removeVal(row,col);
    }
    /*
        removeVal(int row,int col) - if the number on the field at the given coordinates is not fixed, removes it,
        i.e. places zero instead of it, i.e. turns it into an empty cell. Otherwise returns false.
     */
    private boolean removeVal(int row,int col){
        if(this.isFixed[row][col]==true){
            return false;
        }
        int val=this.values[row][col];
        this.rowHasValue[row][val-1]=false;
        this.colHasValue[col][val-1]=false;
        this.subGridHasValue[row/3][col/3][val-1]=false;
        this.values[row][col]=0;
        return true;
    }
    /*
        readFrom(File file) - this method accepts a file that contains a sudoku field,
        then the method reads and assigns values from the file to the local variable values[][]
     */
    public void readFrom(File file)throws FileNotFoundException {
        Scanner input=new Scanner(file);
        for(int i=0;i<this.dim;i++){
            for(int j=0;j<this.dim;j++){
                int val=input.nextInt();
                this.place(val,i,j);
                if(val!=0){
                    this.isFixed[i][j]=true;
                }
            }
        }
    }
    /*
        valIsFixed(row,col) - checks if the value at given coordinates is fixed
     */
    public boolean valIsFixed(int row, int col){
        return this.isFixed[row][col];
    }
    /*
        subGridHasVal(row,col,val) - checks if the the specific subgrid already has a specific value inside
     */
    public boolean subGridHasVal(int row,int col,int val){
        return this.subGridHasValue[row][col][val-1];
    }
    /*
        rowHasVal(row,val) - checks if the specific row already has a specific value inside
     */
    public boolean rowHasVal(int row,int val){
        return this.rowHasValue[row][val-1];
    }
    /*
        colHasVal(col,val) - checks if the specific col already has a specific value inside
     */
    public boolean colHasVal(int col,int val){
        return this.colHasValue[col][val-1];
    }
    /*
        toString() - overridden method from Object class that returns a string that shows us the Sudoku field.
     */
    public String toString(){
        String result="";
        result+=" -------------------------------------";
        result+="\n";
        for(int i=0;i<this.dim;i++){
            result+=" |  ";
            for(int j=0;j<this.dim;j++){
                result+=this.values[i][j];
                if(j%3==2){
                    result+="  |  ";
                    continue;
                }
                result+="  ";
            }
            result+="\n";
            if(i%3==2){
                result+=" -------------------------------------";
                result+="\n";
            }
        }
        return result;
    }
}
