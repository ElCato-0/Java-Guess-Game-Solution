package game;

public class Result {
    public static final Result CORRECT = new Result("correct");
    public static final Result HIGH = new Result("high");
    public static final Result LOW = new Result("low");

    public static final Result HIGH_MOST = new Result("high, at most");
    public static final Result LOW_MOST = new Result("low, at most");
    public static final Result HIGH_LEAST = new Result("high, at least");
    public static final Result LOW_LEAST = new Result("low, at least");

    public String name;
    public int hint;

    public Result(String _name) {
        name = _name;
    }

    public Result(String _name, int _hint) {
        this(_name);
        hint = _hint;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Result) {
            return name.equals(((Result) other).name);
        } else if (other instanceof String) {
            return name.equals(other);
        }
        return false;
    }
}
