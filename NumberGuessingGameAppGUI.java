import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGameAppGUI {

    private static final int MAX_ATTEMPTS = 6;
    private int numberToGuess;
    private int attemptsUsed;
    private int totalScore;
    private int totalRounds;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberGuessingGameAppGUI().createAndShowGUI());
    }
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Number Guessing Game Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to the Number Guessing Game Application ", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        JTextArea instructionsArea = new JTextArea(5, 30);
        instructionsArea.setText("Game Instructions:\n" +
                "1. A random number between 1 and 100 will be generated.\n" +
                "2. You have " + MAX_ATTEMPTS + " attempts to guess the number.\n" +
                "3. Feedback will be provided if your guess is too high, too low, or correct.\n" +
                "4. Aim for a high score!\n");
        instructionsArea.setEditable(false);
        panel.add(instructionsArea);

        JButton startGameButton = new JButton("Start New Game");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(startGameButton);

        JTextArea gameArea = new JTextArea(10, 30);
        gameArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameArea);
        panel.add(scrollPane);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(exitButton);

        Random random = new Random();

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberToGuess = random.nextInt(100) + 1;
                attemptsUsed = 0;
                totalRounds++;
                gameArea.setText("Round " + totalRounds + " starts now! Try to guess the number.\n");

                for (int i = 0; i < MAX_ATTEMPTS; i++) {
                    String input = JOptionPane.showInputDialog(frame, "Attempt " + (i + 1) + " of " + MAX_ATTEMPTS + "\nGuess a number between 1 and 100:");
                    if (input == null) {
                        gameArea.append("Game canceled.\n");
                        return;
                    }
                    try {
                        int userGuess = Integer.parseInt(input);
                        if (userGuess < 1 || userGuess > 100) {
                            JOptionPane.showMessageDialog(frame, "Enter a number between 1 and 100.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            i--;
                            continue;
                        }
                        attemptsUsed++;
                        if (userGuess == numberToGuess) {
                            JOptionPane.showMessageDialog(frame, "Congratulations! You guessed the correct number in " + attemptsUsed + " attempt(s).", "Correct Guess", JOptionPane.INFORMATION_MESSAGE);
                            totalScore += (MAX_ATTEMPTS - attemptsUsed + 1);
                            gameArea.append("Correct! Your score: " + totalScore + "\n");
                            break;
                        } else if (userGuess < numberToGuess) {
                            gameArea.append("Attempt " + (i + 1) + ": Too low! Try a higher number.\n");
                        } else {
                            gameArea.append("Attempt " + (i + 1) + ": Too high! Try a lower number.\n");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid input! Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        i--;
                    }
                }
                if (attemptsUsed == MAX_ATTEMPTS) {
                    JOptionPane.showMessageDialog(frame, "You ran out of attempts! The correct number was " + numberToGuess + ".", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    gameArea.append("Game over! The correct number was " + numberToGuess + ".\n");
                }
                gameArea.append("Total Score: " + totalScore + " | Rounds Played: " + totalRounds + "\n");
            }
        });
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Thanks for playing!\nTotal Rounds: " + totalRounds + "\nFinal Score: " + totalScore, "Exit Game", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });
        frame.add(panel);
        frame.setVisible(true);
    }
}