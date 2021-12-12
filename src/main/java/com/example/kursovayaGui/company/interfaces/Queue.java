package com.example.kursovayaGui.company.interfaces;

import com.example.kursovayaGui.company.classes.MemoryBlock;
import com.example.kursovayaGui.company.classes.Process;

public interface Queue {

    public int getLastProcessId();

    public void add(Process process);
}
