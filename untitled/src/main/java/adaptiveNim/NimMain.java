package adaptiveNim;

import java.util.Scanner;
import java.util.Random;

public class NimMain {

    private Status status;
    private WisdomAboutPositions wisdomAboutPositions = new WisdomAboutPositions();

    public static void main(String[] args) {
        NimMain nimMain = new NimMain();
        nimMain.printGameRules();
        nimMain.status = new Status(nimMain.askInitLevel());
        nimMain.playTheGame();
    }

    private void printGameRules(){
        System.out.println("There are matchsticks in four rows. Player must take out at least one matchstick.");
        System.out.println("Only one row can be reduced.");
        System.out.println("Looser take out the last matchstick, so winner must left behind only one.");
        System.out.println();
    }

    private int askInitLevel(){
        Scanner scanner = new Scanner(System.in);
        int result;
        System.out.println("The computer will adapt to your growing gaming skills.");
        System.out.println("Which knowledge level should we start with?");
        System.out.println("(1: absolute zero, 8: knows all possible steps");
        result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    private void playTheGame(){
        do {
            if (playARoundIfUserWins()){
                status.userWins();
            } else {
                status.computerWins();;
            }
        } while(userWantsNextRound());
    }

    private boolean userWantsNextRound(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want a next round? (y/n");
        String answer;
        answer = scanner.nextLine();
        if (answer.toLowerCase().equals("y")){
            return true;
        }
        return false;
    }

    private boolean playARoundIfUserWins(){
        Scanner scanner = new Scanner(System.in);
        Position actualPosition;
        Position endPosition = new Position(0,0,0,0);
        actualPosition = generateStartPosition();
        boolean computerIsNext;
        if (status.userMadeTheFirstStep()){
            computerIsNext = false;
        } else {
            computerIsNext = true;
        }
        status.print();
        actualPosition.print();

        do {
            if (computerIsNext){
                actualPosition = wisdomAboutPositions.suggestNextStep(status.getComputerActualLevel(),
                                actualPosition);
                System.out.println("Computer is the next, press Enter for next round...");
                scanner.nextLine();
                status.print();
                actualPosition.print();

            } else{
                actualPosition = askUserForNextPosition(actualPosition);
                status.print();
                actualPosition.print();
            }
            computerIsNext = !computerIsNext;

        } while (!actualPosition.isEquivalentPosition(endPosition));
        if (computerIsNext){
            System.out.println("Computer is the winner.");
            return false;
        }
        System.out.println("You are the winner!");
        return true;
    }

    private Position generateStartPosition(){
        Position result;
        Random rand = new Random();
        int[] matchstickInRows = new int[4];

        if (status.getUserReachedLevel()>=8 || status.getComputerActualLevel()>=8){
            matchstickInRows[0] = rand.nextInt(8)+2;
            matchstickInRows[1] = rand.nextInt(6)+4;
            matchstickInRows[2] = rand.nextInt(4)+6;
            matchstickInRows[3] = rand.nextInt(2)+8;
            result = new Position(matchstickInRows[0], matchstickInRows[1],
                                    matchstickInRows[2], matchstickInRows[3]);
            return result;
        }
        result = new Position(1,3,5,7);
        return result;
    }

    private Position askUserForNextPosition(Position actualPosition){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your choice row:");
        //scanner.useDelimiter(":");
        int row = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Type your choice matchsticks number:");
        int pieces = scanner.nextInt();
        scanner.nextLine();
        int[] next = new int[4];
        for (int i=0;i<4; i++){
                next[i] = actualPosition.getMatchsticksInRow(i);
        }
        next[row-1] = next[row-1] - pieces;
        Position result = new Position(next[0],next[1],next[2],next[3]);
        return result;
    }
}
