package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

public class TestMultiValueMap_writeObject_NullEmpty_1 {

    private void invokeWriteObject(MultiValueMap multiValueMap, ObjectOutputStream out) throws Exception {
        Method method = MultiValueMap.class.getDeclaredMethod("writeObject", ObjectOutputStream.class);
        method.setAccessible(true);
        method.invoke(multiValueMap, out);
    }

    @Test(expected = IOException.class)
    public void test_writeObject_nullOutputStream() throws Exception {
        MultiValueMap multiValueMap = new MultiValueMap();
        invokeWriteObject(multiValueMap, null);
    }

    @Test
    public void test_writeObject_emptyMap() {
        MultiValueMap multiValueMap = new MultiValueMap();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            invokeWriteObject(multiValueMap, out);
            // No exception should be thrown for an empty map
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown for an empty map");
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_writeObject_nullValueInMap() {
        MultiValueMap multiValueMap = new MultiValueMap();
        multiValueMap.put("key", null); // Assuming put method allows null values
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            invokeWriteObject(multiValueMap, out);
            // No exception should be thrown
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown for a map containing null values");
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}