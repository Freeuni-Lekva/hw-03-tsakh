import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MetropolisesFrame{
    private JFrame frame;
    private Metropolises m;
    private JTextField metropolisField;
    private JTextField continentField;
    private JTextField populationField;
    private JButton addButton;
    private JButton searchButton;
    private JPanel headerPanel;
    private JPanel controlPanel;
    private final String [] columnNames = {"Metropolis", "Continent", "Population"};
    private final String [] matchChoices = {"Exact Match", "Partial Match"};
    private final String [] populationChoices = {"Greater", "Smaller"};
    private JComboBox matchMenu, populationMenu;

    public MetropolisesFrame(){
        frame = new JFrame();
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            m = new Metropolises();
        } catch (Exception e){
            e.printStackTrace();
        }
        setUpTextFields();
        setUpControlPanel();

        JTable t = new JTable(m);
        for (int i = 0; i < columnNames.length; i++){
            t.getColumnModel().getColumn(i).setHeaderValue(columnNames[i]);
        }
        JScrollPane p = new JScrollPane(t);
        frame.add(p, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    private void setUpControlPanel(){
        controlPanel = new JPanel(new GridLayout(5, 1));

        addButton = new JButton("Add");
        addButton.setSize(10, 10);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String met = metropolisField.getText();
                String cont = continentField.getText();
                String popul = populationField.getText();
                try{
                    m.add(met, cont, popul);
                } catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        controlPanel.add(addButton);

        searchButton = new JButton("search");
        searchButton.setSize(10, 10);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    callSearch();
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        controlPanel.add(searchButton);
        setUpPullDownMenus();
        frame.add(controlPanel, BorderLayout.EAST);
    }

    private void setUpPullDownMenus(){
        matchMenu = new JComboBox(matchChoices);
        populationMenu = new JComboBox(populationChoices);
        JPanel options = new JPanel();
        options.setBorder(new TitledBorder("Search Options"));
        options.add(populationMenu);
        options.add(matchMenu);
        controlPanel.add(options);
    }

    private void callSearch(){
        int match = 0;
        if (matchMenu.getSelectedItem().equals(matchChoices[0])){
            match = 1;
        } else if (matchMenu.getSelectedItem().equals(matchChoices[1])){
            match = -1;
        }

        int populationCriteria = 0;
        if (populationMenu.getSelectedItem().equals(populationChoices[0])){
            populationCriteria = 1;
        } else if (populationMenu.getSelectedItem().equals(populationChoices[1])){
            populationCriteria = -1;
        }

        String met = metropolisField.getText();
        String cont  = continentField.getText();
        int popul = 0;
        if (populationField.getText().length() == 0){
            populationCriteria = 0;
        } else {
            popul = Integer.parseInt(populationField.getText());
        }

        if(met.length() == 0 && cont.length() == 0){
            match = 0;
        }

        try {
            m.search(met, cont, popul, match, populationCriteria);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void setUpTextFields(){
        headerPanel = new JPanel();

        metropolisField = new JTextField(10);
        JLabel metropolisLabel = new JLabel("Metropolis:");
        continentField = new JTextField(10);
        JLabel continentLabel = new JLabel("Continent:");
        populationField = new JTextField(10);
        JLabel populationLabel = new JLabel("Population:");

        headerPanel.add(metropolisLabel);
        headerPanel.add(metropolisField);
        headerPanel.add(continentLabel);
        headerPanel.add(continentField);
        headerPanel.add(populationLabel);
        headerPanel.add(populationField);
        frame.add(headerPanel, BorderLayout.NORTH);

    }

    public static void main(String[] args) {
        // GUI Look And Feel
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        MetropolisesFrame frame = new MetropolisesFrame();
    }
}
