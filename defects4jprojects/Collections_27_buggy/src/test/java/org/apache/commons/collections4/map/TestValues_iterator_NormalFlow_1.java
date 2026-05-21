package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

public class TestValues_iterator_NormalFlow_1 {

    @Test
    public void test_iterator_nonEmpty() {
        MyMap<String, String> map = new MyMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Iterator<String> iterator = map.values().iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value1", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value2", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_multipleValues() {
        MyMap<String, String> map = new MyMap<>();
        map.put("keyA", "valueA");
        map.put("keyB", "valueB");
        map.put("keyC", "valueC");

        Iterator<String> iterator = map.values().iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("valueA", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("valueB", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("valueC", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_singleValue() {
        MyMap<String, String> map = new MyMap<>();
        map.put("keyX", "valueX");

        Iterator<String> iterator = map.values().iterator();

        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("valueX", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_empty() {
        MyMap<String, String> map = new MyMap<>();

        Iterator<String> iterator = map.values().iterator();

        Assert.assertFalse(iterator.hasNext());
    }

    private static class MyMap<K, V> {
        private final java.util.Map<K, V> internalMap = new java.util.HashMap<>();

        public void put(K key, V value) {
            internalMap.put(key, value);
        }

        public Values<K, V> values() {
            return new Values<>(internalMap);
        }

        public java.util.Set<K> keySet() {
            return internalMap.keySet();
        }
    }

    private static class Values<K, V> {
        private final java.util.Map<K, V> map;

        public Values(java.util.Map<K, V> map) {
            this.map = map;
        }

        public Iterator<V> iterator() {
            final IteratorChain<V> chain = new IteratorChain<>();
            for (final K k : map.keySet()) {
                chain.addIterator(new ValuesIterator<>(map.get(k)));
            }
            return chain;
        }
    }

    private static class ValuesIterator<V> implements Iterator<V> {
        private final V value;
        private boolean hasNext = true;

        public ValuesIterator(V value) {
            this.value = value;
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public V next() {
            hasNext = false;
            return value;
        }
    }

    private static class IteratorChain<V> implements Iterator<V> {
        private Iterator<V> currentIterator;
        private int currentIndex = 0;
        private final List<Iterator<V>> iterators = new ArrayList<>();

        public void addIterator(Iterator<V> iterator) {
            iterators.add(iterator);
        }

        @Override
        public boolean hasNext() {
            while (currentIndex < iterators.size()) {
                if (currentIterator == null || !currentIterator.hasNext()) {
                    currentIterator = iterators.get(currentIndex++);
                }
                if (currentIterator.hasNext()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public V next() {
            if (hasNext()) {
                return currentIterator.next();
            }
            throw new NoSuchElementException();
        }
    }
}