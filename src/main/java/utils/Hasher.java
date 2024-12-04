package utils;
import java.util.Random;

public class Hasher {
    public static int generateTransactionCode(int userID, int bookID) {
        int hash = (userID * 31) + bookID;
        Random random = new Random(hash);
        int transactionCode = Math.abs(random.nextInt(1_000_000));
        return transactionCode;
    }
}
