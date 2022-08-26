package adaptiveNim;

import java.util.Scanner;

public class NimMain {

    private Status status;


    public static void main(String[] args) {
        WisdomAboutPositions wisdomAboutPositions = new WisdomAboutPositions();
        NimMain nimMain = new NimMain();
        nimMain.printGameRules();
        nimMain.status = new Status(nimMain.askInitLevel());





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
        if (answer.toLowerCase().equals("n")){
            return true;
        }
        return false;
    }

    private boolean playARoundIfUserWins(){
        Position actualPosition;
        Position endPosition = new Position(0,0,0,0);
        actualPosition = generateStartPosition();
        boolean computerIsNext;
        boolean userNotGiveUp = true;
        if (status.userMadeTheFirstStep()){
            computerIsNext = false;
        } else {
            computerIsNext = true;
        }
        do {

        } while (userNotGiveUp && !actualPosition.isEquivalentPosition(endPosition));
    }
}
