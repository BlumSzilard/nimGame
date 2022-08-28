package adaptiveNim;

public class Status {
    private int userReachedLevel;
    private int computerActualLevel;
    private int roundCounter;
    private int userReachedWins;
    private boolean userMadeTheFirstStep;

    public Status(int computerActualLevel){
        this.computerActualLevel = computerActualLevel;
        userReachedLevel = 1;
        roundCounter = 1;
        userReachedWins = 0;
        userMadeTheFirstStep = true;
    }

    public int getUserReachedLevel() {
        return userReachedLevel;
    }

    public int getComputerActualLevel() {
        return computerActualLevel;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public int getUserReachedWins() {
        return userReachedWins;
    }

    public boolean userMadeTheFirstStep() {
        return userMadeTheFirstStep;
    }

    public void userWins(){
        if (computerActualLevel > userReachedLevel) {
            userReachedLevel = computerActualLevel;
            System.out.println("********************************");
            System.out.println("* You achieved the next level! *");
            System.out.println("********************************");
        }
        roundCounter++;
        userReachedWins++;
        userMadeTheFirstStep = !userMadeTheFirstStep;
        if (userReachedWins > (roundCounter / 2)) {
            computerActualLevel++;
        }
    }

    public void computerWins(){
        roundCounter++;
        userMadeTheFirstStep = !userMadeTheFirstStep;
        if (userReachedWins < (roundCounter / 2) && computerActualLevel > 1) {
            computerActualLevel--;
        }
    }

    public void print(){
        System.out.printf("Round:%d    wins/total:%d|%d    level:%d",this.roundCounter,this.userReachedWins,
                (this.roundCounter-1),this.userReachedLevel);
        System.out.println();
    }
}
