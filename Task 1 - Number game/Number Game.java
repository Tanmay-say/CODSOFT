import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends JFrame {

    private JTextField guessField;
    private JButton guessButton;
    private JLabel feedbackLabel;
    private int secretNumber;
    private int maxAttempts = 10;
    private int attempts = 0;
    private int roundsWon = 0;
    private int totalAttempts = 0;

    public Main() {
        super("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        initializeUI();
        startNewGame();

        setVisible(true);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        guessField = new JTextField();
        guessButton = new JButton("Guess");
        feedbackLabel = new JLabel();

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        JPanel buttonPanel = new JPanel();

        inputPanel.add(new JLabel("Enter your guess: "));
        inputPanel.add(guessField);

        buttonPanel.add(guessButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(feedbackLabel, BorderLayout.SOUTH);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
    }

    private void startNewGame() {
        Random random = new Random();
        secretNumber = random.nextInt(100) + 1;
        attempts = 0;
        feedbackLabel.setText("Guess the number between 1 and 100.");
        guessButton.setEnabled(true);
    }

    private void checkGuess() {
        try {
            int userGuess = Integer.parseInt(guessField.getText());

            if (userGuess == secretNumber) {
                feedbackLabel.setText("Congratulations! You guessed the correct number.");
                endRound();
            } else if (userGuess < secretNumber) {
                feedbackLabel.setText("Too low. Try again.");
            } else {
                feedbackLabel.setText("Too high. Try again.");
            }

            attempts++;
            if (attempts == maxAttempts) {
                feedbackLabel.setText("Sorry, you've reached the maximum number of attempts. The correct number was " + secretNumber + ".");
                endRound();
            }
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    private void endRound() {
        guessButton.setEnabled(false);
        totalAttempts += attempts + 1; // Adding 1 because attempts start from 0
        roundsWon++;

        int option = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            displayFinalScore();
            dispose();
        }
    }

    private void displayFinalScore() {
        JOptionPane.showMessageDialog(this, "Game Over!\nRounds played: " + roundsWon +
                "\nTotal attempts: " + totalAttempts, "Final Score", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
