public class Main {
    private static final int SIZE = 10000000;
    private static final int HALF = SIZE / 2;
    private float[] arr = new float[SIZE];

    public Main() {
        refresh();
    }

    public static void main(String[] args) {
        Main main = new Main();

        System.out.println("Начало работы method1");
        long a = System.currentTimeMillis();
        main.method1();
        System.out.println("Конец работы method1. Время: " + (System.currentTimeMillis() - a) + " мс");

        main.refresh();

        System.out.println("Начало работы method2");
        a = System.currentTimeMillis();
        main.method2();
        System.out.println("Конец работы method2. Время: " + (System.currentTimeMillis() - a) + " мс");
    }

    public void refresh() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
    }

    public void method1() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public void method2() {
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) *
                            Math.cos(0.4f + (i + HALF) / 2));
                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
    }
}
