import junit.framework.TestCase;
import org.junit.Test;

import java.sql.SQLException;

public class MetropolisesTests extends TestCase {
    @Test
    public void test1(){
        try {
            Metropolises m = new Metropolises();
            int rowsBefore = m.search("Telavi", "Europe", 1000, 1, 1);
            int n = m.getRowCount();
            assertEquals(m.add("Telavi", "Europe", "1001"), true);
            assertEquals(rowsBefore + 1, m.search("Telavi", "Europe", 1000, 1, 1));
            System.out.println(m.getColumnCount());
            assertEquals(n + 1, m.getRowCount());
            assertEquals(3, m.getColumnCount());
            for (int i = 0; i < 3; i++){
                System.out.println(m.getValueAt(0, i));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test2(){
        try {
            Metropolises m = new Metropolises();
            int rowsBefore = m.search("Telavi", "Europe", 1000, 1, 1);
            int n = m.getRowCount();
            assertEquals(m.add("", "Europe", "1001"), false);
            assertEquals(rowsBefore, m.search("Telavi", "Europe", 1000, 1, 1));
            assertEquals(n, m.getRowCount());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test3(){
        try {
            Metropolises m = new Metropolises();
            int rowsBefore = m.search("", "Europe", 1000, 1, 1);
            int n = m.getRowCount();
            assertEquals(m.add("Batumi", "Europe", "1001"), true);
            assertEquals(rowsBefore + 1, m.search("", "Europe", 1000, 1, 1));
            assertEquals(n + 1, m.getRowCount());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test4(){
        try {
            Metropolises m = new Metropolises();
            int rowsBefore = m.search("", "Europe", 1000, 1, 0);
            int n = m.getRowCount();
            assertEquals(m.add("Ozurgeti", "Europe", "10010"), true);
            assertEquals(rowsBefore + 1, m.search("", "Europe", 1000, 1, 0));
            assertEquals(n + 1, m.getRowCount());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test5(){
        try {
            Metropolises m = new Metropolises();
            int rowsBefore = m.search("", "", 1000, 0, 0);
            int n = m.getRowCount();
            assertEquals(m.add("Ozurgeti", "Europe", "10010"), true);
            assertEquals(rowsBefore + 1, m.search("", "", 1000, 0, 0));
            assertEquals(n + 1, m.getRowCount());
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void test6(){
        try {
            Metropolises m = new Metropolises();
            int rowsBefore = m.search("", "e", 1000, -1, -1);
            int n = m.getRowCount();
            assertEquals(m.add("Ozurgeti", "Europe", "110"), true);
            assertEquals(rowsBefore + 1, m.search("", "e", 1000, -1, -1));
            assertEquals(n + 1, m.getRowCount());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
