

/*
Create a class Game, which allows a user to play
"Guess the Number" game once.
Game should have the following methods :
- Constructor to generate the random number
- takeUserInput() to take a user input of number
- isCorrectNumber() to detect whether the entered number by the user is true
- getter and setter for noOfGuesses.

Use properties such as noOfGuesses(int). etc to get this task done!
*/
import java.util.Random;
import java.util.Scanner;

class Game {
    private int noOfGuesses = 0;
    int score = (100 - noOfGuesses) / 100;
    int cpu_number, user_number;

    public Game() {
        Random rand = new Random();
        this.cpu_number = rand.nextInt(100) + 1;
    }

    public void takeUserInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        this.user_number = sc.nextInt();
    }

    public boolean isCorrectNumber() {
        return cpu_number == user_number;
    }

    public int getNoOfGuesses() {
        return noOfGuesses;
    }

    public void setNoOfGuesses(int num) {
        noOfGuesses = num;
    }

}

public class guessTheNo {
    public static void main(String[] args) {
        Game newGame = new Game();
        int guess = newGame.getNoOfGuesses();
        while (true) {
            newGame.takeUserInput();

            if (newGame.user_number > newGame.cpu_number) {
                System.out.println("Your number is greater than the cpu number.");
                newGame.setNoOfGuesses(++guess);
            }

            else if (newGame.user_number < newGame.cpu_number) {
                System.out.println("Your number is smaller than the cpu number");
                newGame.setNoOfGuesses(++guess);
            }

            if (newGame.isCorrectNumber()) {
                System.out.println("Congratulations you won the game!!!");
                break;
            }
        }
        System.out.println("Your Score: " + (100 - guess));
    }
}
