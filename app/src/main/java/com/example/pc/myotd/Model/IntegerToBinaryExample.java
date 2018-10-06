package com.example.pc.myotd.Model;

/**
 * Created by PIER on 24/01/2016.
 */
public class IntegerToBinaryExample

{

    public static void main(String[] args)

    {

        int integer = 127;

        String binary = Integer.toBinaryString(integer);

        System.out.println("Binary value of " + integer + " is "

                + binary + ".");



        int original = Integer.parseInt(binary, 2);

        System.out.println("Integer value of binary '" + binary

                + "' is " + original + ".");

    }

}
