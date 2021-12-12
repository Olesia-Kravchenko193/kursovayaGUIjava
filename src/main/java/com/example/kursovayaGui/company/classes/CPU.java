package com.example.kursovayaGui.company.classes;

public class CPU implements Runnable  {
   static Core[] cores;
   public Process process;

    public CPU(Process process) {
        this.process = process;
        //инициализация
    }

    public static void initial(){
        for (int i=0;i<cores.length;i++){
            cores[i] = new Core(i);
        }
        //изначальная инициализация ядер
    }

    public static Core[] getCores() {
        return cores;
    }

    @Override
    public String toString() {
        String result = "CPU:\n{\n";
        for (var core:cores) {
            result += core.isFree()+"\n";
        }
        result += "}\n";
        return result;
    }

    @Override
    public void run() {
        //метод запускаемый в отдельном потоке
        if(process.getId() != 0){
            for (Core core:cores) {
                if(core.isFree() == true){
                    //занимает ядро процессом
                    core.isFree(false);
                    core.setProcessID(process.getId());
                    core.setBurstTime(process.getBurstTime());
                    break;
                }
            }

            for (int i =0;i<process.getBurstTime();i++){
                try {
                    incBurstTime(process.getId());
                    //уменьшает время отработки на процессоре на 1 после прохождения задержки
                    Thread.sleep(Configuration.cpuSleapField);
                    //выполняется работа процесса на ядре
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //освобождение блока памяти от процесса
            //и изменение у процесса состояние на завершенный
            MemoryScheduler.releaseMemoryBlock(process.getId());
            ProcessQueue.changeState(process.getId(),State.Finished);

            for (Core core :cores){
                if(core.getProcessID() == process.getId()){
                    core.isFree(true);
                    core.setProcessID(0);
                    //освобождение ядра от процесса
                }
            }
        }
    }

    private void incBurstTime(int processID){
        for (Core core: cores) {
            if(core.getProcessID() == processID){
                //уменьшение на 1 значения burstTime (class Core)
                core.incBurstTime();
            }
        }
    }
}
