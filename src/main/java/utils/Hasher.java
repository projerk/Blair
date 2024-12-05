package utils;
import java.util.Random;

public class Hasher {
    /**
     * Generates a unique transaction code based on the provided user ID and book ID.
     *
     * @param userID The ID of the user. Must be a positive integer.
     * @param bookID The ID of the book. Must be a positive integer.
     * @return A unique transaction code as a non-negative integer.
     */
    public static int generateTransactionCode(int userID, int bookID) {
        int hash = (userID * 31) + bookID;
        Random random = new Random(hash);
        int transactionCode = Math.abs(random.nextInt(1_000_000));
        return transactionCode;
    }
}
