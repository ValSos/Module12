import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FirstTask {
    public static void timeFromStart(long timeForWork) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);


        executorService.scheduleAtFixedRate(

                () -> {
                    long currentTime = System.currentTimeMillis();
                    System.out.println("Time: " + ((currentTime - startTime)/1000));
                },
                0,
                1,
                TimeUnit.SECONDS
        );


        executorService.scheduleAtFixedRate(
                () -> System.out.println("Минуло 5 секунд"),
                5,
                5,
                TimeUnit.SECONDS
        );
        Thread.sleep(timeForWork);
        executorService.shutdown();

    }


    public static void main(String[] args) throws InterruptedException {
        timeFromStart(15000);
    }

}
