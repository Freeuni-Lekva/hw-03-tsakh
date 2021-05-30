import junit.framework.TestCase;
import org.junit.Test;
public class tests extends TestCase {
    @Test
    //easy grid
    public void test1(){
        Sudoku s = new Sudoku(Sudoku.easyGrid);
        System.out.println(s.toString());
        assertEquals(1, s.solve());
        System.out.println(s.getSolutionText());
        System.out.println(s.getElapsed());
    }
    //medium grid
    public void test2(){
        Sudoku s = new Sudoku(Sudoku.mediumGrid);
        System.out.println(s.toString());
        assertEquals(1, s.solve());
        System.out.println(s.getSolutionText());
        System.out.println(s.getElapsed());
    }

    //hard grid
    public void test3(){
        Sudoku s = new Sudoku(Sudoku.hardGrid);
        System.out.println(s.toString());
        assertEquals(1, s.solve());
        System.out.println(s.getSolutionText());
        System.out.println(s.getElapsed());
    }

    //build board with text
    public void test4(){
        Sudoku s = new Sudoku(Sudoku.textToGrid("3 0 0 0 0 0 0 8 0" +
                "0 0 1 0 9 3 0 0 0" +
                "0 4 0 7 8 0 0 0 3" +
                "0 9 3 8 0 0 0 1 2" +
                "0 0 0 0 4 0 0 0 0" +
                "5 2 0 0 0 6 7 9 0" +
                "6 0 0 0 2 1 0 4 0" +
                "0 0 0 5 3 0 9 0 0" +
                "0 3 0 0 0 0 0 5 1"));
        assertEquals(6, s.solve());
    }

    public void test5(){
        String[]s = new String[0];
        Sudoku.main(s);
    }

    //test for invalid input exception
    public void test6(){
        try {
            Sudoku.textToGrid("1 2 3");
        } catch (Exception e){

        }
    }

    public void testMaxSolution(){
        Sudoku s = new Sudoku(Sudoku.textToGrid("0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0"));
        assertEquals(s.MAX_SOLUTIONS, s.solve());
        System.out.println(s.getElapsed());
    }

    public void testNoSolution(){
        Sudoku s = new Sudoku("1 2 3 4 5 6 7 8 9" +
                "1 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0" +
                "0 0 0 0 0 0 0 0 0");
        assertEquals(0, s.solve());
        System.out.println(s.getElapsed());
    }

}
