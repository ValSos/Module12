import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class SecondTask {
    private final int n;
    public static volatile AtomicInteger number = new AtomicInteger(1);
    public BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public SecondTask(int n) {
        this.n = n;
    }

    public synchronized void add(String element) {
        queue.add(element);
    }

    public synchronized void fizz() {
        while (number.get() <= n) {
            if (number.get() % 3 == 0 && number.get() % 5 != 0) {
                add("fizz");
                number.incrementAndGet();
                notifyAll();

            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void buzz() {
        while (number.get() <= n) {
            if (number.get() % 3 != 0 && number.get() % 5 == 0) {
                add("buzz");
                number.incrementAndGet();
                notifyAll();

            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void fizzbuzz() {
        while (number.get() <= n) {
            if (number.get() % 3 == 0 && number.get() % 5 == 0) {
                add("fizzbuzz");
                number.incrementAndGet();
                notifyAll();

            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public synchronized void number() {
        while (number.get() <= n) {
            if (number.get() % 3 != 0 && number.get() % 5 != 0) {
                add(String.valueOf(number));
                number.incrementAndGet();
                notifyAll();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void print() {
        boolean flag = true;
        while (flag) {
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while (!queue.isEmpty()) {
                System.out.println(queue.poll());
            }
            if (queue.isEmpty()) {
                flag = false;
            }
        }
    }


    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);
        SecondTask test = new SecondTask(15);
        service.submit(test::fizz);
        service.submit(test::buzz);
        service.submit(test::fizzbuzz);
        service.submit(test::number);
        service.submit(test::print);
        service.shutdown();
    }
}
