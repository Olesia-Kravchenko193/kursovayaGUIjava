package com.example.kursovayaGui.company.classes;

public class ClockGenerator implements Runnable {
    private static int time = 0;
    private static volatile boolean shutDown = false;

    public static void incTime(){
        time++;
        //увеличивает занчение на 1
    }

    public static int getTime() {
        return time;
    }

    public static void setShutDown(boolean value){
        shutDown = value;
    }

    @Override
    public void run() {
        while (!shutDown){
            incTime();
            //увеличивает значение на 1 раз в 1 секунду
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        time=0;
    }
}
