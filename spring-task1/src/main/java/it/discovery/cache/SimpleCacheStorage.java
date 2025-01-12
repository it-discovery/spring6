package it.discovery.cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCacheStorage<K, V> implements CacheStorage<K, V> {

    private Map<K, V> map = new ConcurrentHashMap<>();

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(map.get(key));
    }
}
