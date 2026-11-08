package com.example.pc.myotd.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CapoTest {

    private static Capo newCapo() {
        return new Capo(1, Categoria.BUSTO, IttenColor.Rosso, IttenColor.Nero, "Acme",
                new int[4], new int[4], new int[3], "Maglia");
    }

    @Test
    void constructorStoresFields() {
        Capo c = newCapo();
        assertEquals(1, c.getId());
        assertEquals(Categoria.BUSTO, c.getCategoria());
        assertEquals("Acme", c.getMarca());
        assertEquals("Maglia", c.getTipo());
        assertEquals(IttenColor.Rosso.numCode(), c.getColor());
    }

    @Test
    void setCategoriaParsesNamesCaseInsensitively() {
        Capo c = newCapo();
        c.setCategoria("testa");
        assertEquals(Categoria.TESTA, c.getCategoria());
        c.setCategoria("GAMBE");
        assertEquals(Categoria.GAMBE, c.getCategoria());
        c.setCategoria("Piedi");
        assertEquals(Categoria.PEDI, c.getCategoria());
        c.setCategoria("unknown");
        assertEquals(Categoria.PEDI, c.getCategoria(), "unknown names leave category unchanged");
    }

    @Test
    void setColor1AsStringMapsEveryEnumName() {
        Capo c = newCapo();
        for (IttenColor color : IttenColor.values()) {
            c.setColor1AsString(color.name());
            assertEquals(color.numCode(), c.getColor(), color.name());
        }
    }

    @Test
    void setColor1AsStringIgnoresUnknownName() {
        Capo c = newCapo();
        c.setColor1AsString("Fucsia");
        assertEquals(IttenColor.Rosso.numCode(), c.getColor());
    }

    @Test
    void decimalStagioniEncodesSeasonsAsBitmask() {
        Capo c = newCapo();
        assertEquals(0, c.getDecimalStagioni());
        c.setStagione("inverno");
        assertEquals(1, c.getDecimalStagioni());
        c.setStagione("PRIMAVERA");
        assertEquals(5, c.getDecimalStagioni());
        c.setStagione("autunno");
        c.setStagione("estate");
        assertEquals(15, c.getDecimalStagioni());
    }

    @Test
    void stagioniAsString() {
        Capo c = newCapo();
        c.setStagioni(new int[]{1, 0, 0, 1});
        assertEquals(" Inverno  Estate ", c.getStagioniAsString());
    }

    @Test
    void occasioniParsingAndFormatting() {
        Capo c = newCapo();
        c.setOccasione("casual");
        c.setOccasione("Elegante");
        assertArrayEquals(new int[]{0, 1, 1}, c.getOccasioni());
        assertEquals(" Casual  Elegante ", c.getOccasioniAsString());
    }

    @Test
    void condizioniParsingAndFormatting() {
        Capo c = newCapo();
        c.setCondizioni("SOLE");
        c.setCondizioni("neve");
        assertEquals(" Sole  Neve ", c.getCondizioniAsString());
    }

    @Test
    void toStringListsAllAttributes() {
        Capo c = newCapo();
        c.setOccasione("sportivo");
        c.setStagione("estate");
        c.setCondizioni("vento");
        String expected = "BUSTO\nMaglia\nRosso\nAcme\n Sportivo \n Estate \n Vento \n";
        assertEquals(expected, c.toString());
    }

    @Test
    void subclassesBehaveAsCapo() {
        Capo[] items = {
            new Testa(2, Categoria.TESTA, IttenColor.Blu, IttenColor.Nero, "A", new int[4], new int[4], new int[3], "Cappello"),
            new Busto(3, Categoria.BUSTO, IttenColor.Blu, IttenColor.Nero, "B", new int[4], new int[4], new int[3], "Maglia"),
            new Gambe(4, Categoria.GAMBE, IttenColor.Jeans, IttenColor.Nero, "C", new int[4], new int[4], new int[3], "Jeans"),
            new Piedi(5, Categoria.PEDI, IttenColor.Marrone, IttenColor.Nero, "D", new int[4], new int[4], new int[3], "Scarpe"),
        };
        Categoria[] expected = {Categoria.TESTA, Categoria.BUSTO, Categoria.GAMBE, Categoria.PEDI};
        for (int i = 0; i < items.length; i++) {
            assertEquals(expected[i], items[i].getCategoria());
            assertEquals(i + 2, items[i].getId());
        }
    }
}
