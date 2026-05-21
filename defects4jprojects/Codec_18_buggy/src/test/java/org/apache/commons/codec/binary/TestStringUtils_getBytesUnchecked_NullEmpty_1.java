package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUnchecked_NullEmpty_1 {

    @Test
    public void test_getBytesUnchecked_nullString() {
        byte[] result = StringUtils.getBytesUnchecked(null, "UTF-8");
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUnchecked_emptyString() {
        byte[] result = StringUtils.getBytesUnchecked("", "UTF-8");
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesUnchecked_validStringAndCharset() {
        byte[] result = StringUtils.getBytesUnchecked("test", "UTF-8");
        Assert.assertArrayEquals(new byte[]{116, 101, 115, 116}, result);
    }

    @Test
    public void test_getBytesUnchecked_invalidCharset() {
        try {
            StringUtils.getBytesUnchecked("test", "invalid-charset");
            Assert.fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}