package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesIso88591_BoundaryValues_1 {

    @Test
    public void test_getBytesIso8859_1_emptyString() {
        byte[] result = StringUtils.getBytesIso8859_1("");
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesIso8859_1_singleCharacter() {
        byte[] result = StringUtils.getBytesIso8859_1("A");
        Assert.assertArrayEquals(new byte[]{65}, result); // 'A' is 65 in ISO-8859-1
    }

    @Test
    public void test_getBytesIso8859_1_nullString() {
        byte[] result = StringUtils.getBytesIso8859_1(null);
        Assert.assertNull(result);
    }
}