import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApp {
    private JFrame frame;
    private JLabel questionLabel, timerLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton nextButton;
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeRemaining = 9; 
    private Timer timer;
    public QuizApp() {
        questions = new ArrayList<>();
        questions.add(new Question("Which of the following option leads to the portability and security of Java?",
                new String[]{"Bytecode is executed by JVM", "The applet makes the Java code secure and portable", "Use of exception handling", "Dynamic binding between objects"}, "Bytecode is executed by JVM"));
        questions.add(new Question("Which of the following is not a Java features?",
                new String[]{"Dynamic", "Architecture Neutral", "Use of pointers", "Object-oriented"}, "Use of pointers"));
        questions.add(new Question("What does the expression float a = 35 / 0 return?",
                new String[]{"0", "Not a number", "Infinity", "Run time exception"}, "Infinity"));
        questions.add(new Question("What is the return type of the hashCode() method in the Object class?",
                new String[]{"Object", "Int", "Long", "void"}, "Int"));
        frame = new JFrame("Quiz App");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JPanel questionPanel = new JPanel(new GridLayout(5, 1));
        questionLabel = new JLabel("Question will appear here");
        questionPanel.add(questionLabel);
        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionGroup.add(options[i]);
            questionPanel.add(options[i]);
        }

        frame.add(questionPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        timerLabel = new JLabel("Time remaining: " + timeRemaining + "s");
        nextButton = new JButton("Next");
        nextButton.addActionListener(new NextButtonListener());

        bottomPanel.add(timerLabel, BorderLayout.WEST);
        bottomPanel.add(nextButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        loadQuestion(currentQuestionIndex);
        startTimer();

        frame.setVisible(true);
    }
    private void loadQuestion(int index) {
        if (index < questions.size()) {
            Question currentQuestion = questions.get(index);
            questionLabel.setText(currentQuestion.getQuestion());
            String[] opts = currentQuestion.getOptions();
            for (int i = 0; i < options.length; i++) {
                options[i].setText(opts[i]);
                options[i].setSelected(false);
            }
        } else {
            endQuiz();
        }
    }
    private void startTimer() {
        timeRemaining = 10;
        timerLabel.setText("Time remaining: " + timeRemaining + "s");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    timerLabel.setText("Time remaining: " + timeRemaining + "s");
                } else {
                    timer.cancel();
                    nextButton.doClick(); 
                }
            }
        }, 1000, 1000);
    }

    private void endQuiz() {
        frame.dispose();
        JOptionPane.showMessageDialog(null, "Quiz Over! Your score is: " + score + "/" + questions.size());
    }

    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel(); 
            boolean answered = false;
            for (JRadioButton option : options) {
                if (option.isSelected()) {
                    answered = true;
                    if (option.getText().equals(questions.get(currentQuestionIndex).getAnswer())) {
                        score++;
                    }
                    break;
                }
            }

            if (!answered) {
                JOptionPane.showMessageDialog(frame, "Time's up or no answer selected! Moving to the next question.");
            }
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                loadQuestion(currentQuestionIndex);
                startTimer();
            } else {
                endQuiz();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizApp::new);
    }
}
class Question {
    private String question;
    private String[] options;
    private String answer;

    public Question(String question, String[] options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }
}