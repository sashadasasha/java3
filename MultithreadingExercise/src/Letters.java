public class Letters implements Runnable {

    private String message;
    private final Main lock;
    private int threadNumber;

    public Letters(String message, Main lock, int threadNumber) {
        this.message = message;
        this.lock = lock;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        while(lock.status < 13) {
            synchronized (lock) {

                while(!((lock.status % 3) == 0) && threadNumber == 1){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while(!((lock.status % 3) == 1) && threadNumber == 2){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while(!((lock.status % 3) == 2) && threadNumber == 3){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(message);
                lock.status++;
                if ((lock.status % 3) == 0) System.out.print("   ");
                lock.notifyAll();
            }
        }
    }
}
