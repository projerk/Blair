package app;

import java.util.HashMap;
import utils.Hasher;

public class Pooling<T> {
    private HashMap<Integer, T> pools;

    public Pooling() {
        pools = new HashMap<>();
    }

    public <K> void add(K key, T value) {
        int hashKey = Hasher.generateHash(key);

        if (!pools.containsKey(key)) {
            pools.put(hashKey, value);
        }
    }

    public <K> T get(K key) {
        int hashKey = Hasher.generateHash(key);
        return pools.get(hashKey);
    }
}
