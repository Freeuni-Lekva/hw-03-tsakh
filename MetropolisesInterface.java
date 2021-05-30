import java.sql.SQLException;

public interface MetropolisesInterface {
    /** based on given parameters, adds new data to the table
     * @param metropolis name of metropolis
     * @param continent name of continent
     * @param population number of people living in metropolis
     * @return true if row is added successfully, otherwise return false
     */
    boolean add(String metropolis, String continent, String population) throws SQLException;

    /** based on given parameters, searches for valid results and displays
     * @param metropolis name of metropolis
     * @param continent name of continent
     * @param population number of people living in metropolis
     * @param exactMatch if it's -1, continent or metropolis should contain the given string
     *                   if it's 1, result's metropolis or continent name should be the same as given
     *                   if it's 0, we don't care about metropolis and continent names
     * @param populationCriteria if it's -1, then we need areas which has lower number of population,
     *                           if it's 1, then we need areas with greater number of population
     *                           if it's 0, then we don't care about population number
     * @return number of rows found
     */
    int search(String metropolis, String continent, int population, int exactMatch,
                int populationCriteria) throws SQLException;
}
