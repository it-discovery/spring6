package it.discovery.cache;

import java.util.Optional;

public class NoOpCacheStorage<K, V> implements CacheStorage<K, V> {
    @Override
    public void put(K key, V value) {
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.empty();
    }
}
