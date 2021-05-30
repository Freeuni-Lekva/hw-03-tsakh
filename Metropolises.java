import javax.swing.table.AbstractTableModel;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Row
{
    public String metropolis, continent;
    public int population;
};

public class Metropolises extends AbstractTableModel implements MetropolisesInterface{
    private final String url = "jdbc:mysql://localhost:3306/MYDATABASE";
    private final String userName = "root";
    private final String password = "RameParoli#83"; // delete it later
    private final String driver = "com.mysql.cj.jdbc.Driver";

    private Connection connection;
    private Statement statement;
    private final String columnNamesListed = "metropolis, continent, population";
    private final String metropolisTableName = "metropolises";
    private final int numberOfColumns = 3;
    private List<Row> dataRows;

    /** connects database and sets up statement for add method
     */
    public Metropolises() throws Exception{
        dataRows = new ArrayList<>();
        Class.forName(driver);
        connection = DriverManager.getConnection(url, userName, password);
        statement = connection.createStatement();
    }

    @Override
    public boolean add(String metropolis, String continent, String population) throws SQLException{
        if (metropolis.length() == 0 || continent.length() == 0 || population.length() == 0) return false;
        Row curr = new Row();
        curr.metropolis = metropolis;
        curr.continent = continent;
        curr.population = Integer.parseInt(population);
        dataRows.add(curr);

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO metropolises ( " + columnNamesListed + " ) VALUES (");
        queryBuilder.append(" '" + metropolis + "'," );
        queryBuilder.append(" '" + continent + "'," );
        queryBuilder.append(" '" + population + "' );" ); // +'0' for population??
        statement.execute(queryBuilder.toString());
        fireTableDataChanged(); // should be fireTableRowsInserted but have no first and last indexes
        return true;
    }

    private String singleMatchQuery(String s, int matchType){
        String res = "";
        if (s.length() == 0) return res;
        if (matchType == -1){
            res += "LIKE '%" + s + "%' ";
        }
        if (matchType == 1){
            res += "= '" + s + "'";
        }
        return res;
    }

    private String generateMatchQuery(String metropolis, String continent, int matchType){
        String res = "";
        String metropolisQuery = singleMatchQuery(metropolis, matchType);
        boolean and = false;
        if (metropolisQuery.length() != 0){
            and = true;
            res += "metropolis " + metropolisQuery;
        }
        String continentQuery = singleMatchQuery(continent, matchType);
        if (continentQuery.length() != 0){
            if (and){
                res += " AND ";
            }
            res += "continent " + continentQuery;
        }
        return res;
    }

    public String generatePopulationQuery(int population, int criteria){
        String res = "";
        if (criteria == -1){
            res += "population <= " + population; // < ?
        }
        if (criteria == 1){
            res += "population > " + population;
        }
        return res;
    }

    int getNewRows(String query) throws SQLException{
        int count = 0;
        dataRows.clear();
        ResultSet res = statement.executeQuery(query);
        while(res.next()){
            count++;
            Row curr = new Row();
            curr.metropolis = res.getString("metropolis");
            curr.continent = res.getString("continent");
            curr.population = res.getInt("population");
            dataRows.add(curr);
        }
        fireTableDataChanged();
        return count;
    }

    @Override
    public int search(String metropolis, String continent, int population, int exactMatch, int populationCriteria) throws SQLException{
        StringBuilder query = new StringBuilder();
        query.append("select * from " +  metropolisTableName);
        if (populationCriteria != 0 || exactMatch != 0){ // has at least one criteria
            query.append(" WHERE ");
            boolean and = false;
            if (exactMatch != 0){
                and = true;
                query.append(generateMatchQuery(metropolis, continent, exactMatch));
            }
            if (populationCriteria != 0){
                if(and){
                    query.append(" AND ");
                }
                query.append(generatePopulationQuery(population, populationCriteria));
            }
        }
        query.append(";");
        return getNewRows(query.toString());
    }

    @Override
    public int getRowCount() {
        return dataRows.size();
    }


    @Override
    public int getColumnCount() {
        return numberOfColumns;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= dataRows.size()) return null;
        Row r = dataRows.get(rowIndex);
        if(columnIndex == 0){
            return r.metropolis;
        } else if (columnIndex == 1){
            return r.continent;
        }
        return r.population;
    }
}
