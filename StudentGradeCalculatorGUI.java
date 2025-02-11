import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeCalculatorGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeCalculatorGUI().createAndShowGUI());
    }
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Student Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel(" Welcome to the Student Grade Calculator", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        JLabel subjectsLabel = new JLabel("Enter the number of subjects:");
        panel.add(subjectsLabel);

        JTextField numSubjectsField = new JTextField();
        panel.add(numSubjectsField);

        JButton proceedButton = new JButton("Proceed");
        panel.add(proceedButton);

        JTextArea resultsArea = new JTextArea(7, 20);
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        panel.add(scrollPane);

        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numSubjects = Integer.parseInt(numSubjectsField.getText());
                    if (numSubjects <= 0) {
                        JOptionPane.showMessageDialog(frame, "Please enter a positive number of subjects.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int totalMarks = 0;
                    for (int i = 1; i <= numSubjects; i++) {
                        String input = JOptionPane.showInputDialog(frame, "Enter marks for Subject " + i + " (out of 100):");
                        if (input == null) return;

                        int mark;
                        try {
                            mark = Integer.parseInt(input);
                            if (mark < 0 || mark > 100) {
                                JOptionPane.showMessageDialog(frame, "Marks must be between 0 and 100.", "Input Error", JOptionPane.ERROR_MESSAGE);
                                i--;
                                continue;
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            i--;
                            continue;
                        }
                        totalMarks += mark;
                    }

                    double averagePercentage = (double) totalMarks / numSubjects;
                    String grade;

                    if (averagePercentage >= 90) {
                        grade = "A+";
                    } else if (averagePercentage >= 80) {
                        grade = "A";
                    } else if (averagePercentage >= 70) {
                        grade = "B+";
                    } else if (averagePercentage >= 60) {
                        grade = "B";
                    } else if (averagePercentage >= 50) {
                        grade = "C";
                    } else {
                        grade = "F";
                    }

                    resultsArea.setText("Final Results \n" +
                            "Total Marks Obtained: " + totalMarks + " out of " + (numSubjects * 100) + "\n" +
                            "Average Percentage: " + String.format("%.2f", averagePercentage) + "%\n" +
                            "Grade: " + grade);

                    if (grade.equals("F")) {
                        resultsArea.append(" You have failed. Better luck next time!");
                    } else {
                        resultsArea.append(" Congratulations! Keep up the good work!");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number of subjects.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}