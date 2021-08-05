package Sudoku;
public class Solver {
    private Field f;
    public Solver(Field givenField){
        this.f=givenField;
    }
    public boolean solve(int n){
        return this.solveSudoku(n);
    }
    private boolean solveSudoku(int n){
        boolean foundSol=false;
        int row=n/9;
        int col=n%9;
        if(n==81){
            return true;
        }
        if(f.valIsFixed(row,col)){
            foundSol=this.solveSudoku(n+1);
        }else {
            for(int i=1;i<10;i++){
                if(f.colHasVal(col,i) || f.rowHasVal(row,i) || f.subGridHasVal(row/3,col/3,i)){
                    continue;
                }else {
                    f.place(i,row,col);
                    foundSol=this.solveSudoku(n+1);
                    if(foundSol){
                        break;
                    }
                    f.remove(row,col);
                }
            }
        }
        return foundSol;
    }
}
