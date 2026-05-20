package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestMultiValueMap_readObject_1 {

    private void invokeReadObject(MultiValueMap<String, String> multiValueMap, ObjectInputStream in) throws Exception {
        Method method = MultiValueMap.class.getDeclaredMethod("readObject", ObjectInputStream.class);
        method.setAccessible(true);
        method.invoke(multiValueMap, in);
    }

    @Test
    public void test_readObject_validStream() {
        try {
            MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
            // Prepare a valid serialized object
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{/* serialized data */});
            ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
            invokeReadObject(multiValueMap, in);
            assertNotNull(multiValueMap); // Ensure the map is not null after deserialization
        } catch (IOException e) {
            fail("Unexpected IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            fail("Unexpected ClassNotFoundException: " + e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_readObject_invalidStream() {
        try {
            MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(new byte[]{1, 2, 3}));
            invokeReadObject(multiValueMap, in);
            fail("Expected IOException or ClassNotFoundException was not thrown.");
        } catch (IOException e) {
            // Expected exception
        } catch (ClassNotFoundException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_readObject_emptyStream() {
        try {
            MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(new byte[]{}));
            invokeReadObject(multiValueMap, in);
            fail("Expected IOException or ClassNotFoundException was not thrown.");
        } catch (IOException e) {
            // Expected exception
        } catch (ClassNotFoundException e) {
            // Expected exception
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}