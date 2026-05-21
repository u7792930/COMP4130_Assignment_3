package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUsAscii_NullEmpty_1 {

    @Test
    public void test_getBytesUsAscii_nullInput() {
        byte[] result = StringUtils.getBytesUsAscii(null);
        assertNull(result);
    }

    @Test
    public void test_getBytesUsAscii_emptyString() {
        byte[] result = StringUtils.getBytesUsAscii("");
        assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesUsAscii_nonEmptyString() {
        byte[] result = StringUtils.getBytesUsAscii("Hello");
        assertArrayEquals(new byte[]{72, 101, 108, 108, 111}, result);
    }
}