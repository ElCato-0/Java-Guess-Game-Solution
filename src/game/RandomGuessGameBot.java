package game;

import java.util.LinkedHashMap;

import util.Util;
import util.Util.Color;

public class RandomGuessGameBot extends RandomGuessGame {

    private int botLowerRange,
            botUpperRange,
            botGuessNumber;

    private LinkedHashMap<Integer, Double> botGuessScores;

    public RandomGuessGameBot(String _gameMode, String _playerName, Integer _lowerRange, Integer _upperRange) {
        super(_gameMode, _playerName, _lowerRange, _upperRange);
    }

    @Override
    public void reset() {
        super.reset();
        botGuessNumber = (upperRange - lowerRange + 1) / 2 + lowerRange;
        botLowerRange = lowerRange;
        botUpperRange = upperRange;
        botGuessScores = new LinkedHashMap<>();
    }

    public void promptForGuess() {
        Util.print("Guess the number between " + lowerRange + " and " + upperRange +
                ": ");
        Util.sleep(Util.sleepTime * 10);
        Util.print(Color.BLUE + botGuessNumber + Color.RESET + "\n");
        Result result = guess(botGuessNumber);
        Util.print("Your guess is too " + Color.RED + result.name + " " + result.hint + " " + Color.RESET
                + (result.hint == 1 ? "number" : "numbers") + " away from the correct answer.\n");
        Util.print("Your score is now " + scoreStr() + "\n");

        botGuessScores.put(botGuessNumber, score);

        botLogic(result);
    }

    public void botLogic(Result result) {
        if (result == Result.HIGH_LEAST) {
            botUpperRange = botGuessNumber - result.hint;
        } else if (result == Result.LOW_LEAST) {
            botLowerRange = botGuessNumber + result.hint;
        } else if (result == Result.HIGH_MOST) {
            botUpperRange = botGuessNumber;
            if (botLowerRange < botGuessNumber - result.hint) {
                botLowerRange = botGuessNumber - result.hint;
            }
        } else if (result == Result.LOW_MOST) {
            botLowerRange = botGuessNumber;
            if (botUpperRange > botGuessNumber + result.hint) {
                botUpperRange = botGuessNumber + result.hint;
            }
        }
        do {
            botGuessNumber = (botUpperRange - botLowerRange + 1) / 2 + botLowerRange;
            if (result == Result.HIGH_LEAST || result == Result.HIGH_MOST) {
                botUpperRange--;
            }
            else if (result == Result.LOW_LEAST || result == Result.LOW_MOST) {
                botLowerRange++;
            }
            else {
                break;
            }
        } while (botGuessScores.containsKey(botGuessNumber));
    }
}
