package it.discovery.cache;

import java.util.Optional;

public interface CacheStorage<K, V> {

    void put(K key, V value);

    Optional<V> get(K key);
}
