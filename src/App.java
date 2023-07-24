import game.GuessGame;
import game.GuessGameBot;
import game.RandomGuessGame;
import game.RandomGuessGameBot;
import util.Util;

public class App {

    static GuessGame settingsMenu() {

        Util.print("What do you want to do?\n\n");
        Util.print("1. Start a new game\n");
        Util.print("2. View leaderboard\n");
        Util.print("3. Exit\n\n");

        int choice = 0;
        while (choice != 1) {
            Util.print("Enter your choice: ");
            choice = Util.inputInt();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    Util.print("--------------------------------------------------------------------\n");
                    Util.print(Util.readFile("leaderboard.txt"));
                    Util.print("--------------------------------------------------------------------\n");
                    break;
                case 3:
                    System.exit(0);
                default:
                    Util.print("Invalid choice!\n");
            }
        }

        Util.print("Enter your name (optional): ");
        String name = Util.scanner.nextLine().trim();
        if (name.isEmpty())
            name = null;

        int lowerBound = 0,
                upperBound = 0;
        while (lowerBound >= upperBound) {
            Util.print("Enter the guess lower bound, default is 1: ");
            lowerBound = Util.inputInt();
            Util.print("Enter the guess upper bound, default is 20: ");
            upperBound = Util.inputInt();
        }

        Util.print("\nAvailable game modes:\n\n");
        Util.print("1. Normal\n");
        Util.print("2. Random\n");
        Util.print("3. Bot [Normal]\n");
        Util.print("4. Bot [Random]\n");
        Util.print("\n");
        while (true) {
            Util.print("Enter your choice: ");
            choice = Util.inputInt();
            switch (choice) {
                case 1:
                    return new GuessGame("Normal", name, lowerBound, upperBound);
                case 2:
                    return new RandomGuessGame("Random", name, lowerBound, upperBound);
                case 3:
                    return new GuessGameBot("Normal", "Bot", lowerBound, upperBound);
                case 4:
                    return new RandomGuessGameBot("Random", "Bot", lowerBound, upperBound);
                default:
                    Util.print("Invalid choice!\n");
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Util.print("Welcome to Guess Game!\n\n");

        while (true) {
            GuessGame game = settingsMenu();
            while (true) {
                while (game.state == GuessGame.State.PLAYING) {
                    game.promptForGuess();
                }
                Util.print("\n");
                if (game.state == GuessGame.State.WON) {
                    Util.print("Congratulations! You won the game with a score of " + game.scoreStr() + "!\n");
                } else {
                    Util.print("You lost the game!\n");
                }
                Util.print("\n");
                Util.print("Do you want to play again? (y/n): ");
                String choice = Util.scanner.nextLine().trim();
                if (choice.equals("n")) {
                    break;
                }
                game.reset();
            }
        }
    }
}
