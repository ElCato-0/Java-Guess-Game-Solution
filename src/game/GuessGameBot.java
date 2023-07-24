package game;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import util.Util;
import util.Util.Color;

public class GuessGameBot extends GuessGame {

    private int botLowerRange,
            botUpperRange,
            botGuessNumber;

    private LinkedHashMap<Integer, Double> botGuessScores;

    public GuessGameBot(String _gameMode, String _playerName, Integer _lowerRange, Integer _upperRange) {
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
        Util.print("Your guess was too " + result.name + "!\n");
        Util.print("Your score is now " + scoreStr() + "\n");

        botGuessScores.put(botGuessNumber, score);

        botLogic(result);
    }

    public void botLogic(Result result) {
        if (result == Result.LOW) {
            botLowerRange = botGuessNumber;
        } else if (result == Result.HIGH) {
            botUpperRange = botGuessNumber;
        }
        while (botGuessScores.containsKey(botLowerRange)) {
            botLowerRange++;
        }
        while (botGuessScores.containsKey(botUpperRange)) {
            botUpperRange--;
        }

        botGuessNumber = (botUpperRange - botLowerRange + 1) / 2 + botLowerRange;
    }
}
