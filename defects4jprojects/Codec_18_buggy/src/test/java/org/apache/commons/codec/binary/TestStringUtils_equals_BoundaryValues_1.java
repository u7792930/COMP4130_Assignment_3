package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_equals_BoundaryValues_1 {

    @Test
    public void test_equals_bothNull() {
        assertTrue(StringUtils.equals(null, null));
    }

    @Test
    public void test_equals_firstNull() {
        assertFalse(StringUtils.equals(null, "abc"));
    }

    @Test
    public void test_equals_secondNull() {
        assertFalse(StringUtils.equals("abc", null));
    }

    @Test
    public void test_equals_sameString() {
        assertTrue(StringUtils.equals("abc", "abc"));
    }

    @Test
    public void test_equals_caseSensitive() {
        assertFalse(StringUtils.equals("abc", "ABC"));
    }

    @Test
    public void test_equals_singleCharacterStrings() {
        assertTrue(StringUtils.equals("a", "a"));
        assertFalse(StringUtils.equals("a", "b"));
    }

    @Test
    public void test_equals_emptyStrings() {
        assertTrue(StringUtils.equals("", ""));
        assertFalse(StringUtils.equals("", "a"));
        assertFalse(StringUtils.equals("a", ""));
    }

    @Test
    public void test_equals_singleElementCollections() {
        assertTrue(StringUtils.equals("test", "test"));
        assertFalse(StringUtils.equals("test", "TEST"));
    }
}