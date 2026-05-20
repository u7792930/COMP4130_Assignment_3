package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import static org.junit.Assert.*;

public class TestMultiValueMap_iterator_1 {

    private MultiValueMap<String, String> map;

    public MultiValueMapTest() {
        map = new MultiValueMap<>();
    }

    @Test
    public void test_iterator_keyNotInMap() {
        // Arrange
        String key = "nonExistentKey";

        // Act
        Iterator<String> iterator = map.iterator(key);

        // Assert
        Assert.assertNotNull(iterator);
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_keyInMap() {
        // Arrange
        String key = "existingKey";
        map.put(key, "value1");
        map.put(key, "value2");

        // Act
        Iterator<String> iterator = map.iterator(key);

        // Assert
        Assert.assertNotNull(iterator);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value1", iterator.next());
        Assert.assertEquals("value2", iterator.next());
    }

    @Test
    public void test_iterator_emptyMap() {
        // Arrange
        String key = "anyKey";

        // Act
        Iterator<String> iterator = map.iterator(key);

        // Assert
        Assert.assertNotNull(iterator);
        Assert.assertFalse(iterator.hasNext());
    }
}