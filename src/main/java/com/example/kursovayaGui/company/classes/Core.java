package com.example.kursovayaGui.company.classes;

public class Core {
    private boolean isFree;
    private int coreID;
    private int processID;
    private int burstTime;

    Core(int coreID){
        //инициализвация ядра
        this.coreID = coreID;
        isFree = true;
        burstTime =0;
    }


    public boolean isFree() {
        return isFree;
    }

    public void isFree(boolean free) {
        isFree = free;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void incBurstTime() {
        this.burstTime--;
        //уменьшение на 1 burstTime
    }

    public void setBurstTime(int burstTime){
        this.burstTime=burstTime;
    }

    public int getCoreID() {
        return coreID;
    }

    @Override
    public String toString(){
        return "Core[" + coreID + "] isFree[" + isFree + "] processID[" + processID + "] BurstTime["+burstTime+"]";
    }
}
