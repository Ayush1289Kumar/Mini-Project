import java.util.Random;
import java.util.Scanner;

public class rockPaper {
    public static void main(String[] args) {
        // Creating scanner object
        Scanner sc = new Scanner(System.in);

        // Creating random object
        Random rand = new Random();

        // Variables declaration
        int user_choice, cpu_choice;
        int user_score = 0, cpu_score = 0;
        System.out.println("\n-----Rock|Paper|Scissors-----\n");

        // ... (imports and scanner setup remain the same)

        while (true) {
            System.out.print("\n1.Rock\n2.Paper\n3.Scissors\nAny other number to exit.\nYour Choice: ");
            user_choice = sc.nextInt();

            if (user_choice < 1 || user_choice > 3)
                break;

            cpu_choice = rand.nextInt(3) + 1;
            System.out.println("Cpu choice: " + cpu_choice);

            if (user_choice == cpu_choice) {
                System.out.println("It's a tie!");
            }
            // Special Case: Rock (1) beats Scissors (3)
            else if (user_choice == 1 && cpu_choice == 3) {
                System.out.println("You Won! (Rock crushes Scissors)");
                user_score++;
            }
            // Special Case: Scissors (3) loses to Rock (1)
            else if (user_choice == 3 && cpu_choice == 1) {
                System.out.println("Cpu Won! (Rock crushes Scissors)");
                cpu_score++;
            }
            // Standard Cases: 2 > 1 (Paper > Rock) or 3 > 2 (Scissors > Paper)
            else if (user_choice > cpu_choice) {
                System.out.println("You Won!");
                user_score++;
            } else {
                System.out.println("Cpu Won!");
                cpu_score++;
            }
        }
        if (user_score == 0 && cpu_score == 0) {
            System.out.println("No Scores Available!!!");
        } else {
            System.out.println("\n---Scores---\n");
            System.out.println("Cpu Score: " + cpu_score);
            System.out.println("Your Score: " + user_score);

            if (cpu_score > user_score) {
                System.out.println("Better luck next time");
            } else {
                System.out.println("Congratulations, You defeated the Cpu");
            }
            System.out.println("Thanks for using...");
        }

    }

}
