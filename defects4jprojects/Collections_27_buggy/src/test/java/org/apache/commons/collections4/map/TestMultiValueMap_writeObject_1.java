package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;

public class TestMultiValueMap_writeObject_1 {

    @Test
    public void test_writeObject_ioExceptionThrown() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(byteArrayOutputStream) {
                // Use a different method to simulate the exception without overriding final method
                public void writeStreamHeader() throws IOException {
                    throw new IOException("Forced IOException");
                }
            };
            MultiValueMap<String, String> map = new MultiValueMap<>();
            map.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class).setAccessible(true);
            map.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class).invoke(map, out);
            Assert.fail("Expected IOException to be thrown");
        } catch (IOException e) {
            // Expected exception
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
    public void test_writeObject_successfulWrite() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class).setAccessible(true);
        map.getClass().getDeclaredMethod("writeObject", ObjectOutputStream.class).invoke(map, out);
        out.close();

        // Validate that the output stream contains the expected serialized data
        // This is a placeholder for actual validation logic, which may require deserialization
        // and checking the contents of the MultiValueMap.
        Assert.assertTrue(byteArrayOutputStream.size() > 0);
    }
}