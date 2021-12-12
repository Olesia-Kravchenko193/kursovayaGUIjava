package com.example.kursovayaGui.company.classes;

import java.util.ArrayList;
import java.util.Queue;

public class Scheduler implements Runnable {
    //поля класса
    ProcessQueue queue;
    RejectQueue rejectQueue;
    CPU cpu;
    MemoryScheduler memoryScheduler;
    ClockGenerator clockGenerator;
    static volatile boolean shutDown;

    public Scheduler(final int cpuCoresNumber) {
        clockGenerator= new ClockGenerator();
        new Thread(clockGenerator).start();//запуск метода run(класс ClockGenerator) в отдельном потоке
        //инициализация очереди и очереди отказов а так же планировщика памяти
        this.queue = new ProcessQueue();
        this.rejectQueue = new RejectQueue();
        this.memoryScheduler = new MemoryScheduler();
        this.shutDown = false;
        //установка колличества ядер
        CPU.cores = new Core[cpuCoresNumber];
        //инициализация ядер
        CPU.initial();
        initial();
    }

    private void initial(){
        //инициализация планировщика памяти
        MemoryScheduler.initial();
        //добавление в очередь процессов ОС
        queue.add("OS",1);

    }

    public void generateProcess(final int N){
        //создание  N-ого колличества процессов
        for (int i =0 ;i<N;i++){
            Process process = new Process();
            MemoryBlock mb = MemoryScheduler.findFreeBlock(process.getMemory());
            //проверяет есть ли свободный блок памяти для процесса
            if(mb.getStart() == 0 ){
                //если нету - добавляет в очередь отказов
                rejectQueue.add(process);
            }else{
                //если есть - добавляет в очередь на обработку процессором
                synchronized (queue){
                    queue.add(process,mb);
                }
            }
        }
    }

    @Override
    public void run()  {
        try {
            //выставляет значение для shutDown в других классах,для корректно работы многопоточности
            ProcessQueue.setShutDown(false);
            new Thread(queue).start();//запуск метод run(класс ProcessQueue) в отдельном потоке
            //метод который рапределяет процессы по ядрам
            giveCPUWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void giveCPUWork() throws InterruptedException {
        //запуск в новом потоке
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!shutDown){
                    //добавление новых процессов
                    generateProcess(CPU.getCores().length+2);
                    try {
                        Thread.sleep((Configuration.maxProcessWorked*Configuration.cpuSleapField)/2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }

        });
        thread.start();
        while (!shutDown){
            int coresFreeNumber = 0;
            //поиск свободных ядер
            for (Core core:cpu.getCores()) {
                if(core.isFree()==true){
                    coresFreeNumber++;
                }
            }
            if (shutDown){
                break;
                //на случай если shutDown изменилась
            }
            for(int i=0;i<coresFreeNumber;i++){
                Process process = queue.getHighPriorityProcess();
                //получение процесса с наивысшим приоритетом
                ProcessQueue.changeState(process.getId(),State.Running);
                //изменения статуса процесса
                CPU cpu = new CPU(process);
                //инициализация
                new Thread(cpu).start();//запуск работы на процессоре в отдельном потоке
            }
            if (shutDown){
                break;
                //на случай если shutDown изменилась
            }
            if(rejectQueue.isNeadDefragmentation()){
                //првоекра нужна ли дефрагментация памяти
                MemoryScheduler.defragmentationStart();
            }
            Thread.sleep(Configuration.cpuSleapField);
        }
        //установка значений shutDown для остановки многоптока
        ProcessQueue.setShutDown(shutDown);
    }

    public static void setShutDown(boolean value){
        shutDown = value;
    }

    @Override
    public String toString() {
        String result ="Cores:[ ";
        for (Core core: cpu.getCores()){
            result+=core.getProcessID()+"\t"+core.isFree()+"\t";
        }
        result+="]\n";
        result+=queue.toString("queue");
        result+=memoryScheduler.toString();
        return result;
    }

    public ArrayList returnStatistic(){
        ArrayList statistic = new ArrayList();
        statistic.add(queue.getQueue());
        statistic.add(queue.getFinishedQueue());
        statistic.add(rejectQueue.getRejectQueue());
        statistic.add(CPU.getCores());
        statistic.add(MemoryScheduler.getMemoryBlocks());
        return statistic;
    }

}

