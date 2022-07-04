import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description
 * @Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * @Date 2022/6/28 16:51
 * @Tags
 **/
@SpringBootTest(classes = ExecutorServiceTest.class)
public class ExecutorServiceTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable runnableTask = () -> {
            try {
                Long startTime = System.currentTimeMillis();
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.println("Test......");
                System.out.println(System.currentTimeMillis() - startTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(300);
            return "Task's execution";
        };

        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        List<Future<String>> futures = executor.invokeAll(callableTasks);
        for(Future<String> future : futures) {
            System.out.println(future.get());
        }

        ForkJoinPool commonPool = ForkJoinPool.commonPool();


    }
}
