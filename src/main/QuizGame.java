package main;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main class to run the quiz game
public class QuizGame {
    public static void main(String[] args) {
        // Create questions
        List<Question> questions = new ArrayList<>();
        questions.add(new MultipleChoiceQuestion(
                "What is the capital of France?",
                List.of("London", "Paris", "Berlin", "Madrid"),
                1
        ));
        questions.add(new TrueFalseQuestion(
                "The Earth is flat.",
                false
        ));
        questions.add(new MultipleChoiceQuestion(
                "Which of these is not a programming language?",
                List.of("Java", "Python", "HTML", "C++"),
                2
        ));
        questions.add(new TrueFalseQuestion(
                "The sun rises in the east.",
                true
        ));

        // Create quiz and start it
        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}

// Abstract base class for all question types
abstract class Question {
    protected String text;
    
    public Question(String text) {
        this.text = text;
    }
    
    public abstract void displayQuestion();
    public abstract boolean checkAnswer(int userAnswer);
    public abstract void displayOptions();
}

// Multiple choice question type
class MultipleChoiceQuestion extends Question {
    private List<String> options;
    private int correctOption;
    
    public MultipleChoiceQuestion(String text, List<String> options, int correctOption) {
        super(text);
        this.options = options;
        this.correctOption = correctOption;
    }
    
    @Override
    public void displayQuestion() {
        System.out.println(text);
    }
    
    @Override
    public void displayOptions() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }
    
    @Override
    public boolean checkAnswer(int userAnswer) {
        return userAnswer - 1 == correctOption;
    }
}

// True/false question type
class TrueFalseQuestion extends Question {
    private boolean correctAnswer;
    
    public TrueFalseQuestion(String text, boolean correctAnswer) {
        super(text);
        this.correctAnswer = correctAnswer;
    }
    
    @Override
    public void displayQuestion() {
        System.out.println(text);
    }
    
    @Override
    public void displayOptions() {
        System.out.println("1. True");
        System.out.println("2. False");
    }
    
    @Override
    public boolean checkAnswer(int userAnswer) {
        boolean userChoice = (userAnswer == 1);
        return userChoice == correctAnswer;
    }
}

// Quiz class to manage the game flow
class Quiz {
    private List<Question> questions;
    private int score;
    private Scanner scanner;
    
    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.score = 0;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("Welcome to the Quiz Game!");
        System.out.println("You will be asked " + questions.size() + " questions.\n");
        
        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ":");
            currentQuestion.displayQuestion();
            currentQuestion.displayOptions();
            
            System.out.print("Your answer (enter the number): ");
            int userAnswer = scanner.nextInt();
            
            if (currentQuestion.checkAnswer(userAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect!");
            }
        }
        
        displayResult();
    }
    
    private void displayResult() {
        System.out.println("\nQuiz completed!");
        System.out.println("Your final score: " + score + "/" + questions.size());
        double percentage = (double) score / questions.size() * 100;
        System.out.printf("Percentage: %.1f%%\n", percentage);
        
        if (percentage >= 75) {
            System.out.println("Excellent!");
        } else if (percentage >= 50) {
            System.out.println("Good job!");
        } else {
            System.out.println("Keep practicing!");
        }
    }
}