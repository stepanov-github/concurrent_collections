import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static BlockingQueue<String> count_a = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> count_b = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> count_c = new ArrayBlockingQueue<>(100);
    public static int max_a = 0;
    public static int max_b = 0;
    public static int max_c = 0;
    public static String max_a_text;
    public static String max_b_text;
    public static String max_c_text;
    public static final int LENGTH = 100000;
    public static final int COUNTTEXTS = 10000;

    public static void main(String[] args) throws InterruptedException {
        Thread addQueue = new Thread(() -> {
            for (int i = 0; i < COUNTTEXTS; i++) {
                String str = generateText("abc", LENGTH);
                try {
                    count_a.put(str);
                    count_b.put(str);
                    count_c.put(str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread chek_a = new Thread(() -> {
            for (int i = 0; i < COUNTTEXTS; i++) {
                try {
                    String text = count_a.take();
                    int count_a = 0;
                    for (int j = 0; j < text.length(); j++) {
                        if (text.charAt(j) == 'a') {
                            count_a++;
                        }
                    }
                    if (count_a > max_a) {
                        max_a = count_a;
                        max_a_text = text;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread chek_b = new Thread(() -> {
            for (int i = 0; i < COUNTTEXTS; i++) {
                try {
                    String text = count_b.take();
                    int count_b = 0;
                    for (int j = 0; j < text.length(); j++) {
                        if (text.charAt(j) == 'b') {
                            count_b++;
                        }
                    }
                    if (count_b > max_b) {
                        max_b = count_b;
                        max_b_text = text;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread chek_c = new Thread(() -> {
            for (int i = 0; i < COUNTTEXTS; i++) {
                try {
                    String text = count_c.take();
                    int count_c = 0;
                    for (int j = 0; j < text.length(); j++) {
                        if (text.charAt(j) == 'c') {
                            count_c++;
                        }
                    }
                    if (count_c > max_c) {
                        max_c = count_c;
                        max_c_text = text;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        addQueue.start();
        chek_a.start();
        chek_b.start();
        chek_c.start();

        addQueue.join();
        chek_a.join();
        chek_b.join();
        chek_c.join();

        System.out.println("Максиманое количество символов а = " + max_a + "Текст :");
        System.out.println(max_a_text);
        System.out.println("Максиманое количество символов b = " + max_b + "Текст :");
        System.out.println(max_b_text);
        System.out.println("Максиманое количество символов c = " + max_c + "Текст :");
        System.out.println(max_c_text);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


}
