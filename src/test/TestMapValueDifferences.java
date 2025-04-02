package test;

import main.MapValueDifferences;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMapValueDifferences {
    private Map<String, Integer> map1;
    private Map<String, Integer> map2;

    @BeforeEach
    void setUp() {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
    }

    @Test
    void testBothMapsNull() {
        assertEquals(Collections.emptyMap(), MapValueDifferences.computeValueDifferences(null, null));
    }

    @Test
    void testFirstMapNull() {
        map2.put("a", 3);
        Map<String, Integer> expected = Map.of();
        assertEquals(expected, MapValueDifferences.computeValueDifferences(null, map2));
    }

    @Test
    void testSecondMapNull() {
        map1.put("a", 3);
        Map<String, Integer> expected = Map.of();
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, null));
    }

    @Test
    void testBothMapsEmpty() {
        assertEquals(Collections.emptyMap(), MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testFirstMapEmpty() {
        map1 = Map.of();
        map2.put("a", 3);
        Map<String, Integer> expected = Map.of();
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map2, map1));
    }

    @Test
    void testSecondMapEmpty() {
        map1.put("a", 3);
        map2 = Map.of();
        Map<String, Integer> expected = Map.of();
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map2, map1));

    }

    @Test
    void testNoCommonKeys() {
        map1.put("a", 1);
        map2.put("b", 2);
        Map<String, Integer> expected = Map.of();
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testSomeCommonKeys() {
        map1.put("a", 5);
        map1.put("b", 10);
        map1.put("c", 3);
        map2.put("b", 7);
        map2.put("c", 8);
        map2.put("d", 12);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("b", 3);
        expected.put("c", 5);
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testAllCommonKeys() {
        map1.put("a", 5);
        map1.put("b", 10);
        map1.put("c", 3);
        map2.put("a", 12);
        map2.put("b", 7);
        map2.put("c", 8);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 7);
        expected.put("b", 3);
        expected.put("c", 5);
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testAllNegativeValues(){
        map1.put("a", -5);
        map1.put("b", -10);
        map1.put("c", -3);
        map2.put("a", -12);
        map2.put("b", -7);
        map2.put("c", -8);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 7);
        expected.put("b", 3);
        expected.put("c", 5);
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testMixedValues(){
        map1.put("a", -5);
        map1.put("b", 10);
        map1.put("c", 3);
        map2.put("a", 12);
        map2.put("b", -7);
        map2.put("c", -8);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 17);
        expected.put("b", 17);
        expected.put("c", 11);
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testSomeValuesZero() {
        map1.put("a", 0);
        map1.put("b", 10);
        map2.put("a", 5);
        map2.put("b", 0);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 5);
        expected.put("b", 10);
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testAllValuesZero() {
        map1.put("a", 0);
        map1.put("b", 0);
        map2.put("a", 0);
        map2.put("b", 0);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 0);
        expected.put("b", 0);
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testIdenticalValues(){
        map1.put("a", 5);
        map1.put("b", 10);
        map2.put("a", 5);
        map2.put("b", 10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 0);
        expected.put("b", 0);
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }

    @Test
    void testLargeValues() {
        map1.put("a", 1000000);
        map1.put("b", 2000000);
        map2.put("a", 500000);
        map2.put("b", 1000000);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 500000);
        expected.put("b", 1000000);
        assertEquals(expected, MapValueDifferences.computeValueDifferences(map1, map2));
    }
}
