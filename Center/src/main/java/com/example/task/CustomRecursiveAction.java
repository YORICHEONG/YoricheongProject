package com.example.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/28 17:26
 * @Tags
 **/
public class CustomRecursiveAction extends RecursiveAction {

    private String workload = "";

    private static final int THRESHOLD = 4;

    public CustomRecursiveAction(String workload) {
        this.workload = workload;
    }

    /**
     * split the content and struct the new Task list
     * @return
     */
    private List<CustomRecursiveAction> createSubTasks() {
        List<CustomRecursiveAction> subTasks = new ArrayList<>();
        String partOne = workload.substring(0, workload.length() / 2);
        String partTwo = workload.substring(workload.length() / 2);
        subTasks.add(new CustomRecursiveAction(partOne));
        subTasks.add(new CustomRecursiveAction(partTwo));

        return subTasks;
    }

    @Override
    protected void compute() {
        if(workload.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubTasks());
        } else {
            process(workload);
        }
    }

    private void process(String work) {
        String result = work.toUpperCase(Locale.ROOT);
        System.out.println("This result is :" + result + ", process by thread name : " + Thread.currentThread().getName());
    }
}
