package com.example.kursovayaGui.company.classes;

import java.util.Comparator;

public class MemoryBlock {
    private int start;
    private int end;
    private int processId;

    public MemoryBlock(int start,int end,int processId){
        this.start = start;
        this.end = end;
        this.processId = processId;
        //инициализация
    }

    public static Comparator<MemoryBlock> byEnd = new Comparator<MemoryBlock>() {
        //компаратор сортирующий по возврастанию по концу блока памяти
        @Override
        public int compare(MemoryBlock o1, MemoryBlock o2) {
            return o1.getEnd() - o2.getEnd();
        }
    };

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getProcessId() {
        return processId;
    }

    @Override
    public boolean equals(Object o) {
        //метод для сравнения  двух объектов класса MemoryBlock
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemoryBlock that = (MemoryBlock) o;
        return start == that.start && end == that.end && processId == that.processId;
    }

    @Override
    public String toString() {
        return "{" +
                "start=" + start +
                ", end=" + end +
                ", processId=" + processId +
                '}';
    }
}
