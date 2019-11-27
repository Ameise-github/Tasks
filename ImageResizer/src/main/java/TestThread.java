public class TestThread implements Runnable {
    private int n;

    public TestThread(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        System.out.println("result = " + n + "/ name Thred =  " + Thread.currentThread().getName());
//        System.out.printf("%s started... \n", Thread.currentThread().getName());
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            System.out.println("Thread has been interrupted");
//        }
//        System.out.printf("%s fiished... \n", Thread.currentThread().getName());
    }
}
