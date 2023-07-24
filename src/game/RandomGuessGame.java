package game;

import util.Util;
import util.Util.Color;

public class RandomGuessGame extends GuessGame {

    public RandomGuessGame(String _gameMode, String _playerName, Integer _lowerRange, Integer _upperRange) {
        super(_gameMode, _playerName, _lowerRange, _upperRange);
    }

    public Result guess(int guessNumber) {
        Result result = super.guess(guessNumber);
        if (result == Result.CORRECT) {
            return result;
        }

        int distance = Math.abs(randomNumber - guessNumber + 1);
        int randomDistance = distance + (distance / 4) * (random.nextBoolean() ? 1 : -1);

        if (result == Result.HIGH) {
            if (guessNumber - randomDistance < randomNumber) {
                result = Result.HIGH_MOST;
            } else {
                result = Result.HIGH_LEAST;
            }
        } else {
            if (guessNumber + randomDistance > randomNumber) {
                result = Result.LOW_MOST;
            } else {
                result = Result.LOW_LEAST;
            }
        }

        result.hint = randomDistance;
        return result;
    }

    public void promptForGuess() {
        Util.print("Guess the number between " + lowerRange + " and " + upperRange + ": ");
        int guessNumber = Util.inputInt();
        Result result = guess(guessNumber);
        if (!result.equals(Result.CORRECT)) {
            Util.print("Your guess is too " + Color.RED + result.name + " " + result.hint + " " + Color.RESET + (result.hint == 1 ? "number" : "numbers") + " away from the correct answer.\n");
            Util.print("Your score is now " + scoreStr() + "\n");
        }
    }
}
