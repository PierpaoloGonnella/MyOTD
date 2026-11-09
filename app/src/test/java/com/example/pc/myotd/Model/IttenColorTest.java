package com.example.pc.myotd.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IttenColorTest {

    @Test
    void neutralColorsHaveZeroCode() {
        assertEquals(0, IttenColor.Nero.numCode());
        assertEquals(0, IttenColor.Bianco.numCode());
        assertEquals(0, IttenColor.Grigio.numCode());
    }

    @Test
    void complementaryPairsOnIttenWheelSumToZero() {
        IttenColor[][] pairs = {
            {IttenColor.Blu, IttenColor.Arancio},
            {IttenColor.BluViola, IttenColor.ArancioGiallo},
            {IttenColor.Viola, IttenColor.Giallo},
            {IttenColor.VerdeLime, IttenColor.Cremisi},
            {IttenColor.Rosso, IttenColor.Verde},
            {IttenColor.RossoArancio, IttenColor.Turchese},
        };
        for (IttenColor[] p : pairs) {
            assertEquals(0, p[0].numCode() + p[1].numCode(), p[0] + " + " + p[1]);
        }
    }

    @Test
    void specialColorCodes() {
        assertEquals(-10, IttenColor.Jeans.numCode());
        assertEquals(-7, IttenColor.Marrone.numCode());
        assertEquals(-7, IttenColor.BluNavy.numCode());
    }

    @Test
    void enumHasEighteenColors() {
        assertEquals(18, IttenColor.values().length);
    }
}
