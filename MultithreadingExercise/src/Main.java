public class Main {
    volatile int status;

    public static void main(String[] args)  {
        Main lock = new Main();
        Thread t1 = new Thread(new Letters("A", lock,1));
        Thread t2 = new Thread(new Letters("B", lock,2));
        Thread t3 = new Thread(new Letters("C", lock,3));
        t1.start();
        t2.start();
        t3.start();

    }
}
