package Thread;

import java.util.concurrent.*;

public class ThreadEducation {


    static void main(String[] args) throws InterruptedException {
        MyThreadPool pool = new MyThreadPool(3);

        for (int i = 0; i < 10; i++) {
            pool.execute(() -> System.out.println("Мой поток"));
        }
        pool.shutdown();
    }
}