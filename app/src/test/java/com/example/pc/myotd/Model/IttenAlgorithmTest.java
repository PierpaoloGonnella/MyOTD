package com.example.pc.myotd.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IttenAlgorithmTest {

    private static boolean match(IttenColor... colors) {
        Integer[] codes = new Integer[colors.length];
        for (int i = 0; i < colors.length; i++) {
            codes[i] = colors[i].numCode();
        }
        return new IttenAlgorithm(codes).Abbinamento();
    }

    @Test
    void emptyOutfitIsAMatch() {
        assertTrue(new IttenAlgorithm(new Integer[0]).Abbinamento());
    }

    @Test
    void singleNeutralColorIsAMatch() {
        assertTrue(match(IttenColor.Nero));
    }

    @Test
    void singleChromaticColorIsNotAMatch() {
        assertFalse(match(IttenColor.Rosso));
    }

    @Test
    void complementaryPairIsAMatch() {
        assertTrue(match(IttenColor.Rosso, IttenColor.Verde));
        assertTrue(match(IttenColor.Blu, IttenColor.Arancio));
        assertTrue(match(IttenColor.Viola, IttenColor.Giallo));
    }

    @Test
    void identicalChromaticPairIsNotAMatch() {
        assertFalse(match(IttenColor.Viola, IttenColor.Viola));
    }

    @Test
    void nonComplementaryPairIsNotAMatch() {
        assertFalse(match(IttenColor.Rosso, IttenColor.Blu));
    }

    @Test
    void neutralsOnlyAreAMatch() {
        assertTrue(match(IttenColor.Nero, IttenColor.Bianco, IttenColor.Grigio));
    }

    @Test
    void complementaryPairPlusNeutralIsAMatch() {
        assertTrue(match(IttenColor.Rosso, IttenColor.Nero, IttenColor.Verde));
    }
}
