import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class ProducerConsumer {

    
    private static Queue<Integer> buffer = new LinkedList<>();
    private static int bufferSize;
    private static volatile int count = 0;

     
    private static volatile boolean producerBusy = false;
    private static volatile boolean consumerBusy = false;

    
    private static volatile int bufferLock = 0;

    
    public static boolean acquireLock() {
        if (bufferLock == 0) {
            bufferLock = 1;
            return true;
        }
        return false;
    }

  
    public static void releaseLock() {
        bufferLock = 0;
    }

  
    static class Producer extends Thread {
        private final int id;
        private final Random random = new Random();

        Producer(int id) {
            this.id = id;
        }

        public void run() {
            while (true) {
                int value = random.nextInt(100);

                if (!producerBusy && acquireLock()) {
                    if (count < bufferSize) {
                        producerBusy = true;

                        buffer.add(value);
                        count++;

                        System.out.println("Producer " + id + " produced: " + value);
                        System.out.println("Buffer: " + buffer);
                    } else {
                        System.out.println("Producer " + id + " waits — Buffer full");
                    }

                    releaseLock();
                    producerBusy = false;
                }

                try {
                    Thread.sleep(random.nextInt(1000));  
                } catch (InterruptedException e) {
                    System.out.println("Producer " + id + " interrupted.");
                }
            }
        }
    }

  
    static class Consumer extends Thread {
        private final int id;
        private final Random random = new Random();

        Consumer(int id) {
            this.id = id;
        }

        public void run() {
            while (true) {
                if (!consumerBusy && acquireLock()) {
                    if (count > 0) {
                        consumerBusy = true;

                        int value = buffer.poll();
                        count--;

                        System.out.println("Consumer " + id + " consumed: " + value);
                        System.out.println("Buffer: " + buffer);
                    } else {
                        System.out.println("Consumer " + id + " waits — Buffer empty");
                    }

                    releaseLock();
                    consumerBusy = false;
                }

                try {
                    Thread.sleep(random.nextInt(1500)); // random delay
                } catch (InterruptedException e) {
                    System.out.println("Consumer " + id + " interrupted.");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter buffer size: ");
        bufferSize = sc.nextInt();

        
        Producer p1 = new Producer(1);
        Producer p2 = new Producer(2);
        Consumer c1 = new Consumer(1);
        Consumer c2 = new Consumer(2);

        p1.start();
        p2.start();
        c1.start();
        c2.start();

      
        sc.close();
    }
}
