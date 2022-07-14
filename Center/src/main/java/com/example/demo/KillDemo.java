package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Description:
 * Author YORICHEONG yoricheong@gmail.com precent99@qq.com
 * 2022/7/13 9:24
 **/
public class KillDemo {

    private Integer stock = 10;

    private BlockingQueue<RequestPromise> queue = new LinkedBlockingDeque<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        KillDemo killDemo = new KillDemo();
        killDemo.mergeJob();
        Thread.sleep(2000);
        List<Future<Result>> futureList = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final Long orderId = i + 100L;
            final Long userId = Long.valueOf(i);
            Future<Result> future = executorService.submit(() -> {
                countDownLatch.countDown();
                countDownLatch.await(1000, TimeUnit.SECONDS);
                return killDemo.buy(new UserRequest(orderId, userId, 1));
            });
            futureList.add(future);
        }

        futureList.forEach(future -> {
            try{
                Result result = future.get(300, TimeUnit.MILLISECONDS);
                System.out.println(Thread.currentThread().getName() + "Result:" + result.toString());
            }catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Result buy(UserRequest userRequest) throws InterruptedException {
        RequestPromise requestPromise = new RequestPromise(userRequest);
        synchronized (requestPromise) {
            boolean enQueueSuccess = queue.offer(requestPromise, 100, TimeUnit.MILLISECONDS);
            if(!enQueueSuccess) {
                return new Result(false, "System busy");
            }
            try {
                requestPromise.wait(200);
                if(requestPromise.getResult() == null) {
                    return new Result(false, "Process timeout");
                }
            } catch(Exception e) {
                return new Result(false, "Be InterruptedException");
            }
        }
        return requestPromise.getResult();
    }

    public void mergeJob() {
        new Thread(() -> {
            List<RequestPromise> list = new ArrayList<>();
            while(true) {
                if(queue.isEmpty()) {
                    try {
                        Thread.sleep(10);
                        continue;
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int batchSize = queue.size();
                for (int i = 0; i < batchSize; i++) {
                    list.add(queue.poll());
                }

                System.out.println("Sum total reduce count is" + batchSize);

                int sum = list.stream().mapToInt(e -> e.getUserRequest().getCount()).sum();
                if(sum <= stock) {
                    stock -= sum;
                    list.forEach(requestPromise -> {
                        requestPromise.setResult(new Result(true, "ok"));
                        synchronized (requestPromise) {
                            requestPromise.notify();
                        }
                    });
                    list.clear();
                    continue;
                }
                for(RequestPromise requestPromise : list) {
                    int count = requestPromise.getUserRequest().getCount();
                    if(count <= stock) {
                        stock -= count;
                        requestPromise.setResult(new Result(true, "OK"));
                    } else {
                        requestPromise.setResult(new Result(false, "Stock isn't"));
                    }
                    synchronized(requestPromise) {
                        requestPromise.notify();
                    }
                }
                list.clear();
            }
        }, "mergeJob").start();
    }
}
class RequestPromise{
    private UserRequest userRequest;

    private Result result;

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public RequestPromise(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
}
class Result{
    private boolean success;

    private String msg;

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
class UserRequest{
    private Long orderId;

    private Long userId;

    private Integer count;

    public UserRequest(Long orderId, Long userId, Integer count) {
        this.orderId = orderId;
        this.userId = userId;
        this.count = count;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}