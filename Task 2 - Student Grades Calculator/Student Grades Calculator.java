import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradeCalculatorGUI extends JFrame {

    private JTextField[] subjectFields;
    private JLabel totalMarksLabel;
    private JLabel averagePercentageLabel;
    private JLabel gradeLabel;

    public GradeCalculatorGUI() {
        super("Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        initializeUI();

        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new GridLayout(0, 2, 10, 10)); // 0 rows for dynamic rows, 2 columns, with 10-pixel horizontal and vertical gaps

        createSubjectInputFields();
        createCalculateButton();
        createResultLabels();
    }

    private void createSubjectInputFields() {
        int numSubjects = getNumberOfSubjects();
        subjectFields = new JTextField[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Subject " + (i + 1) + ":"));
            subjectFields[i] = new JTextField(5);
            panel.add(subjectFields[i]);
            add(panel);
        }
    }

    private void createCalculateButton() {
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAndDisplayResults();
            }
        });

        add(calculateButton);
    }

    private void createResultLabels() {
        totalMarksLabel = new JLabel("Total Marks: ");
        averagePercentageLabel = new JLabel("Average Percentage: ");
        gradeLabel = new JLabel("Grade: ");

        add(totalMarksLabel);
        add(averagePercentageLabel);
        add(gradeLabel);
    }

    private int getNumberOfSubjects() {
        String input = JOptionPane.showInputDialog("Enter the number of subjects:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return getNumberOfSubjects();
        }
    }

    private void calculateAndDisplayResults() {
        try {
            int[] subjectMarks = new int[subjectFields.length];

            for (int i = 0; i < subjectFields.length; i++) {
                int mark = Integer.parseInt(subjectFields[i].getText());

                // Ensure marks are not above 100
                if (mark < 0 || mark > 100) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Marks should be between 0 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                subjectMarks[i] = mark;
            }

            int totalMarks = calculateTotalMarks(subjectMarks);
            double averagePercentage = calculateAveragePercentage(totalMarks, subjectMarks.length);
            char grade = calculateGrade(averagePercentage);

            totalMarksLabel.setText("Total Marks: " + totalMarks);
            averagePercentageLabel.setText("Average Percentage: " + averagePercentage + "%");
            gradeLabel.setText("Grade: " + grade);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for subject marks.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int calculateTotalMarks(int[] subjectMarks) {
        int totalMarks = 0;

        for (int marks : subjectMarks) {
            totalMarks += marks;
        }

        return totalMarks;
    }

    private double calculateAveragePercentage(int totalMarks, int numSubjects) {
        return ((double) totalMarks / (numSubjects * 100)) * 100;
    }

    private char calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return 'A';
        } else if (averagePercentage >= 80) {
            return 'B';
        } else if (averagePercentage >= 70) {
            return 'C';
        } else if (averagePercentage >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeCalculatorGUI();
            }
        });
    }
}
