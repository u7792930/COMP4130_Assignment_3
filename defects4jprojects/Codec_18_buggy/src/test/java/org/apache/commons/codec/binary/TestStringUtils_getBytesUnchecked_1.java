package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.codec.binary.StringUtils;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUnchecked_1 {

    @Test
    public void test_getBytesUnchecked_nullString() {
        byte[] result = StringUtils.getBytesUnchecked(null, "UTF-8");
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUnchecked_validString() {
        byte[] result = StringUtils.getBytesUnchecked("Hello", "UTF-8");
        Assert.assertArrayEquals(new byte[]{72, 101, 108, 108, 111}, result);
    }

    @Test
    public void test_getBytesUnchecked_invalidCharset() {
        try {
            StringUtils.getBytesUnchecked("Hello", "invalid-charset");
            Assert.fail("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}