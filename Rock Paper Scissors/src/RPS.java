import java.util.Random;

//Deals with Rock, Paper, Scissors logic
public class RPS {

    public static enum Choices { ROCK, PAPER, SCISSORS };
    public static enum Results { WIN, DRAW, LOSE };

    private static Random r = new Random();

    public static Results isPlayerWon(Choices player, Choices cpu) {

        if (player == null | cpu == null) throw new NullPointerException("Input given was null");

        Results resultToReturn = Results.LOSE;

        switch (player) {
            case ROCK:
                if (cpu == Choices.ROCK) resultToReturn = Results.DRAW;
                if (cpu == Choices.PAPER) resultToReturn = Results.LOSE;
                if (cpu == Choices.SCISSORS) resultToReturn = Results.WIN;
                break;
            case PAPER:
                if (cpu == Choices.ROCK) resultToReturn = Results.WIN;
                if (cpu == Choices.PAPER) resultToReturn = Results.DRAW;
                if (cpu == Choices.SCISSORS) resultToReturn = Results.LOSE;
                break;
            case SCISSORS:
                if (cpu == Choices.ROCK) resultToReturn = Results.LOSE;
                if (cpu == Choices.PAPER) resultToReturn = Results.WIN;
                if (cpu == Choices.SCISSORS) resultToReturn = Results.DRAW;
                break;
        }

        return resultToReturn;

    }

    public static Choices doRandom() {
        int rand = r.nextInt(3);

        switch (rand) {
            case 0:
                return Choices.ROCK;
            case 1:
                return Choices.PAPER;
        }

        return Choices.SCISSORS;

    }
}