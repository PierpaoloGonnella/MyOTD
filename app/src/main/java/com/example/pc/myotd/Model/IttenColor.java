package com.example.pc.myotd.Model;

/**
 * Created by pierpawel on 29/12/2015.
 */
public enum IttenColor {

    Nero(0), Bianco(0),
    Grigio(0), Jeans(-10),
    Marrone(-7),BluNavy(-7),
    Blu(1), Arancio(-1),
    BluViola(2), ArancioGiallo(-2),
    Viola(3), Giallo(-3),
    VerdeLime(4), Cremisi(-4),
    Rosso(5), Verde(-5),
    RossoArancio(6), Turchese(-6);

    private final int numcode;
    IttenColor(int num){
        numcode = num;
    }

    final int numCode(){ return numcode;}

}

