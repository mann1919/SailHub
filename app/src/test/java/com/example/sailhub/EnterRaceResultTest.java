package com.example.sailhub;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class EnterRaceResultTest {
    @Test
    public void testCalculateElapsed() {
        EnterRaceResult raceResult = new EnterRaceResult();
        String elapsedTime = raceResult.calculateElapsed("00:22:11",2,4 );
        assertEquals("00:44:22", elapsedTime);
    }

    @Test
    public void testCalculateElapsedNegLaps() {
        EnterRaceResult raceResult = new EnterRaceResult();
        assertThrows(IllegalArgumentException.class, ()
                -> raceResult.calculateElapsed("00:22:11",-2,4 ));
    }


    @Test
    public void testCalculateCorrected() {
        EnterRaceResult raceResult = new EnterRaceResult();
        String correctedTime = raceResult.calculateCorrected("00:44:22",1123 );
        assertEquals("00:39:30", correctedTime);
    }


    @Test
    public void testCalculateCorrectedNegPY() {
        EnterRaceResult raceResult = new EnterRaceResult();
        assertThrows(IllegalArgumentException.class, ()
                -> raceResult.calculateCorrected("00:44:11",-1124 ));
    }

}
