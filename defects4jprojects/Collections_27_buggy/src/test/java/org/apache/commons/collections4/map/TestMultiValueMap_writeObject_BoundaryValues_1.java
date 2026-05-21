package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class TestMultiValueMap_writeObject_BoundaryValues_1 {

    private MultiValueMap<Object, Object> multiValueMap;

    public TestMultiValueMap_writeObject_BoundaryValues_1() {
        multiValueMap = new MultiValueMap<>();
    }

    @Test
    public void test_writeObject_outIsNull() {
        try {
            multiValueMap.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class)
                .setAccessible(true);
            multiValueMap.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class)
                .invoke(multiValueMap, (ObjectOutputStream) null);
            Assert.fail("Expected IOException when out is null");
        } catch (Exception e) {
            Assert.assertTrue(e.getCause() instanceof IOException);
        }
    }

    @Test
    public void test_writeObject_outDefaultWriteObjectThrowsException() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new ByteArrayOutputStream()) {
                @Override
                public void defaultWriteObject() throws IOException {
                    throw new IOException("defaultWriteObject failed");
                }
            };
            multiValueMap.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class)
                .setAccessible(true);
            multiValueMap.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class)
                .invoke(multiValueMap, out);
            Assert.fail("Expected IOException due to defaultWriteObject failure");
        } catch (Exception e) {
            Assert.assertTrue(e.getCause() instanceof IOException);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

    @Test
    public void test_writeObject_singleElementCollection() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new ByteArrayOutputStream());
            multiValueMap.put("key", "value"); // Adding a single element
            multiValueMap.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class)
                .setAccessible(true);
            multiValueMap.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class)
                .invoke(multiValueMap, out);
            // No assertion needed; we expect no exceptions
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

    @Test
    public void test_writeObject_emptyCollection() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new ByteArrayOutputStream());
            multiValueMap.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class)
                .setAccessible(true);
            multiValueMap.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class)
                .invoke(multiValueMap, out);
            // No assertion needed; we expect no exceptions
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }
}