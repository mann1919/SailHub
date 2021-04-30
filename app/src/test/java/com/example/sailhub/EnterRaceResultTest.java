package com.example.sailhub;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        String elapsedTime = raceResult.calculateElapsed("00:22:11",-2,4 );
        assertEquals("00:44:22", elapsedTime);
    }

    @Test
    public void testCalculateCorrected() {
        EnterRaceResult raceResult = new EnterRaceResult();
        String correctedTime = raceResult.calculateCorrected("00:44:22",1123 );
        assertEquals("00:39:30", correctedTime);
    }

}
