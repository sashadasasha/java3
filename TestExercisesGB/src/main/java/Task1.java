import java.util.ArrayList;
import java.util.Arrays;

public class Task1 {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Task1.class);

    public static void main(String[] args) {
        final int[] res2 = process(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        System.out.println(Arrays.toString(res2));
    }

    public static int[] process(int[] arrIn) {
        if(arrIn.length == 0) {
            log.info("В массиве нет элементов");
            return arrIn;
        }
        for (int i = arrIn.length - 1; i >= 0 ; i--) {
            if(arrIn[i] == 4) {
                int backArr[] = new int[arrIn.length - i - 1];
                System.arraycopy(arrIn, (i+1), backArr,0,(arrIn.length - 1 - i));
                return backArr;
            }
        }
        throw new RuntimeException("В массиве нет ни одной 4-ки после которой были бы числа");
    }
}
