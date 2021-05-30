import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	private JTextArea puzzle;
	private JTextArea solution;
	public SudokuFrame() {
		super("Sudoku Solver");
		setLocationByPlatform(true);
		//split in two parts and add names to each
		setLayout(new BorderLayout(4, 4));
		puzzle = new JTextArea(15, 20);
		solution = new JTextArea(15, 20);
		puzzle.setBorder(new TitledBorder("puzzle"));
		add(puzzle, BorderLayout.CENTER);
		solution.setBorder(new TitledBorder("solution"));
		add(solution, BorderLayout.EAST);

		//creating south panel
		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JCheckBox autoCheck = new JCheckBox("Auto check");
		autoCheck.setSelected(true); // to be checked by default
		puzzle.getDocument().addDocumentListener(new DocumentListener() {
			private void automatedSolve(){
				if(!autoCheck.isSelected()) return;
				sudokuSolve();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				automatedSolve();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				automatedSolve();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				automatedSolve();
			}
		});
		JButton check = new JButton("Check");
		check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sudokuSolve();
			}
		});
		controlPanel.add(check, BorderLayout.EAST);
		controlPanel.add(autoCheck, BorderLayout.EAST);
		add(controlPanel, BorderLayout.SOUTH);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	// sets up sudoku and tries to solve it
	private void sudokuSolve(){
		try {
			Sudoku s = new Sudoku(Sudoku.textToGrid(puzzle.getText()));
			int numSol = s.solve();
			solution.setText(s.getSolutionText());
			solution.append("solutions: " + numSol + "\n");
			solution.append("elapsed: " + s.getElapsed());
		} catch (Exception e){
			solution.setText("Parsing problem");
		}
	}
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
