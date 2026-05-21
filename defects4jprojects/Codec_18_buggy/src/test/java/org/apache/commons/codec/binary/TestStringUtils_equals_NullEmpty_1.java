package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_equals_NullEmpty_1 {

    @Test
    public void test_equals_bothNull() {
        Assert.assertTrue(StringUtils.equals(null, null));
    }

    @Test
    public void test_equals_firstNull() {
        Assert.assertFalse(StringUtils.equals(null, "abc"));
    }

    @Test
    public void test_equals_secondNull() {
        Assert.assertFalse(StringUtils.equals("abc", null));
    }
}