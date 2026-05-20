package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.junit.Assert.*;

public class TestMultiValueMap_readObject_1 {

    @Test
    public void test_readObject_invalidStream() {
        try {
            MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
            // Create an invalid ObjectInputStream
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(new byte[]{1, 2, 3}));

            // Attempt to call the readObject method
            multiValueMap.readObject(in);
            fail("Expected IOException or ClassNotFoundException was not thrown.");
        } catch (IOException e) {
            // Expected exception
        } catch (ClassNotFoundException e) {
            // Expected exception
        }
    }

    @Test
    public void test_readObject_nullPointerStream() {
        try {
            MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
            // Attempt to call the readObject method with a null stream
            multiValueMap.readObject(null);
            fail("Expected IOException was not thrown.");
        } catch (IOException e) {
            // Expected exception
        } catch (ClassNotFoundException e) {
            // Not expected, should only be IOException
            fail("Unexpected ClassNotFoundException was thrown.");
        }
    }

    @Test
    public void test_readObject_emptyStream() {
        try {
            MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
            // Create an empty ObjectInputStream
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(new byte[]{}));

            // Attempt to call the readObject method
            multiValueMap.readObject(in);
            fail("Expected IOException or ClassNotFoundException was not thrown.");
        } catch (IOException e) {
            // Expected exception
        } catch (ClassNotFoundException e) {
            // Expected exception
        }
    }
}