package client;

public class Console {
    static Object lockObj = new Object();
    static Object lockObj2 = new Object();

    static class TestLockClass {

        public void test() {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName());

                int i = 0;
                while (true) {
                    i++;// + 1;
                }
            }
        }
    }

    static class TestLockClass2 {

        public void test() {
            synchronized (this) {
                System.out.println(Thread.currentThread().getName());

                int i = 0;
                while (true) {
                    i++;// + 1;
                }
            }
        }
    }

    public static void main(String[] args) {

        TestLockClass lockClass = new TestLockClass();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                lockClass.test();

            });
            t.start();
        }
        TestLockClass2 lockClass2 = new TestLockClass2();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                lockClass2.test();

            });
            t.start();
        }
    }
}
