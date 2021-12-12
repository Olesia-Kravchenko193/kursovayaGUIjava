package com.example.kursovayaGui.company.classes;

import java.util.Random;

public class Utils {
    public static Random random = new Random();

    public static int getRandomInteger(int maxValue ){
        return random.nextInt(maxValue+1 );
    }

    public static int getRandomInteger(int minValue,int maxValue ){
        return minValue + random.nextInt(maxValue - minValue + 1);
    }
}
