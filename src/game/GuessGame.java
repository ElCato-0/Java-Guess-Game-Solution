package game;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;
import util.Util;

public class GuessGame {

    public    int     randomNumber,
                      lowerRange      = 1,
                      upperRange      = 20;
    public    double  score           = 100;
    public    String  gameMode        = "[Normal]";
    public    String  playerName      = "Unknown";
    protected Random  random          = new Random();
    
    public static enum State {
        PLAYING, WON, LOST
    }
    public State state = State.PLAYING;

    public GuessGame(String _gameMode, String _playerName, Integer _lowerRange, Integer _upperRange) {
        // set attributes if not null
        lowerRange = _lowerRange != null ? _lowerRange : lowerRange;
        upperRange = _upperRange != null ? _upperRange : upperRange;
        gameMode   = _gameMode   != null ? _gameMode   : gameMode;
        playerName = _playerName != null ? _playerName : playerName;
        reset();
    }

    public void reset() {
        score = 100;
        state = State.PLAYING;
        randomNumber = random.nextInt(upperRange - lowerRange + 1) + lowerRange;
    }

    public Result guess(int guessNumber) {
        if (guessNumber == randomNumber)  {
            handleWin();
            state = State.WON;
            return Result.CORRECT;
        }
        score -= Math.abs(guessNumber - randomNumber) * 35.0 / (upperRange - lowerRange);
        Result result = guessNumber > randomNumber ? Result.HIGH : Result.LOW;
        if (score <= 0) {
            state = State.LOST;
            score = 0;
        }
        return result;
    }

    public void handleWin() {
        Util.appendToFile("leaderboard.txt", String.format("Score: %03.2f | %-10s| %-10s| %-10s", score, playerName, gameMode, new Date()));
    }

    public void promptForGuess() {
        Util.print("Guess the number between " + lowerRange + " and " + upperRange + ": ");
        int guessNumber = Util.inputInt();
        Result result = guess(guessNumber);
        if (!result.equals(Result.CORRECT)) {
            Util.print("Your guess was too " + result.name + "!\n");
            Util.print("Your score is now " + scoreStr() + "\n");
        }
    }

    public String scoreStr() {
        return String.format("%.3f", score);
    }
}