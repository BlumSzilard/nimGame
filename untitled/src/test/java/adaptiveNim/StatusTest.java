package adaptiveNim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {
    Status status;

    @Test
    void testConstructor(){
        status = new Status(5);

        assertEquals(5, status.getComputerActualLevel());
        assertEquals(1, status.getRoundCounter());
        assertEquals(0,status.getUserReachedWins());
        assertEquals(1,status.getUserReachedLevel());
        assertTrue(status.userMadeTheFirstStep());
    }

    @Test
    void testUserWins(){
        status = new Status(5);
        status.userWins();

        assertEquals(5, status.getComputerActualLevel());
        assertEquals(2, status.getRoundCounter());
        assertEquals(1,status.getUserReachedWins());
        assertEquals(5,status.getUserReachedLevel());
        assertFalse(status.userMadeTheFirstStep());

        status.userWins();

        assertEquals(6, status.getComputerActualLevel());
        assertEquals(3, status.getRoundCounter());
        assertEquals(2,status.getUserReachedWins());
        assertEquals(5,status.getUserReachedLevel());
        assertTrue(status.userMadeTheFirstStep());

        status.userWins();

        assertEquals(7, status.getComputerActualLevel());
        assertEquals(4, status.getRoundCounter());
        assertEquals(3,status.getUserReachedWins());
        assertEquals(6,status.getUserReachedLevel());
        assertFalse(status.userMadeTheFirstStep());

    }

    @Test
    void testComputerWins(){
        status = new Status(2);
        status.computerWins();

        assertEquals(1, status.getComputerActualLevel());
        assertEquals(2, status.getRoundCounter());
        assertEquals(0,status.getUserReachedWins());
        assertEquals(1,status.getUserReachedLevel());
        assertFalse(status.userMadeTheFirstStep());

        status.computerWins();

        assertEquals(1, status.getComputerActualLevel());
        assertEquals(3, status.getRoundCounter());
        assertEquals(0,status.getUserReachedWins());
        assertEquals(1,status.getUserReachedLevel());
        assertTrue(status.userMadeTheFirstStep());

    }

}