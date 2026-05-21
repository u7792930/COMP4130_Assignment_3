package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestMultiValueMap_readObject_NullEmpty_1 {

    private static class TestMultiValueMap extends MultiValueMap<String, String> implements Serializable {
        private static final long serialVersionUID = 1L;

        private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            map = (Map<String, Object>) in.readObject();
        }
    }

    @Test
    public void test_readObject_nullInputStream() {
        TestMultiValueMap map = new TestMultiValueMap();
        ObjectInputStream in = null;
        try {
            map.readObject(in);
            Assert.fail("Expected NullPointerException when input stream is null");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException | ClassNotFoundException e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_readObject_emptyInputStream() {
        TestMultiValueMap map = new TestMultiValueMap();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
            ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
            map.readObject(in);
            Assert.assertTrue("Expected empty map after reading", map.isEmpty());
        } catch (IOException e) {
            Assert.fail("Unexpected IOException");
        } catch (ClassNotFoundException e) {
            Assert.fail("Unexpected ClassNotFoundException");
        }
    }

    @Test
    public void test_readObject_invalidData() {
        TestMultiValueMap map = new TestMultiValueMap();
        byte[] invalidData = new byte[]{1, 2, 3}; // Not a valid serialized object
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(invalidData);
            ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
            map.readObject(in);
            Assert.fail("Expected IOException due to invalid data");
        } catch (IOException e) {
            // Expected exception
        } catch (ClassNotFoundException e) {
            Assert.fail("Unexpected ClassNotFoundException");
        }
    }
}