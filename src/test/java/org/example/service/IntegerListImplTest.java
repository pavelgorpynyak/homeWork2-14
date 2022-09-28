package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class IntegerListImplTest {
    private final IntegerList out = new IntegerListImpl();

    @BeforeEach
    public void setUp() {
        out.add(1);
    }

    @ParameterizedTest
    @NullSource
    public void shouldThrowIfNull( Integer input ) {
        assertThrows(IllegalArgumentException.class, () -> out.add(input));
        assertThrows(IllegalArgumentException.class, () -> out.remove(input));
        assertThrows(IllegalArgumentException.class, () -> out.set(0, input));
        assertThrows(IllegalArgumentException.class, () -> out.indexOf(input));
        assertThrows(IllegalArgumentException.class, () -> out.lastIndexOf(input));
    }

    @Test
    public void shouldAddStringToArrayByItemAndHaveCorrectSize() {
        for (int i = 0; i < 10; i++) {
            out.add(2);
        }
        assertEquals(11, out.size());
    }

    @ParameterizedTest
    @CsvSource({"0,2", "1,2"})
    public void doesntThrowExceptionWhenAddByIndexInBounds( int index, Integer item ) {
        assertDoesNotThrow(() -> out.add(index, item));
    }

    @ParameterizedTest
    @CsvSource({"0,2", "1,2"})
    public void doesntThrowWhenAddByIndexInBounds( int index, Integer toAdd ) {
        assertDoesNotThrow(() -> out.add(index, toAdd));
    }

    @Test
    public void shouldAddByIndexCorrectly() {
        assertDoesNotThrow(() -> out.get(0));
        out.add(0, 2);
        assertEquals(2, out.get(0));
        assertEquals(1, out.get(1));
        out.remove(0);
        out.add(1, 2);
        assertEquals(1, out.get(0));
        assertEquals(2, out.get(1));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1, 2, -10, Integer.MAX_VALUE})
    public void shouldThrowWhenOutOfBounds( int index ) {
        assertThrows(IllegalArgumentException.class, () -> out.get(index));
        assertThrows(IllegalArgumentException.class, () -> out.remove(index));
        assertThrows(IllegalArgumentException.class, () -> out.set(index, 2));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2, 3, -10, Integer.MAX_VALUE})
    public void shouldThrowWhenAddOutOfBounds( int index ) {
        assertThrows(IllegalArgumentException.class, () -> out.add(index, 2));
    }

    @Test
    public void shouldSetCorrectly() {
        out.add(2);
        out.add(3);
        assertDoesNotThrow(() -> out.set(0, 4));
        assertEquals(4, out.get(0));
        assertEquals(2, out.get(1));
        assertEquals(3, out.get(2));
        assertDoesNotThrow(() -> out.set(1, 5));
        assertEquals(4, out.get(0));
        assertEquals(5, out.get(1));
        assertEquals(3, out.get(2));
        assertDoesNotThrow(() -> out.set(2, 6));
        assertEquals(4, out.get(0));
        assertEquals(5, out.get(1));
        assertEquals(6, out.get(2));
    }

    @Test
    public void shouldRemoveByIndexCorrectly() {
        out.add(2);
        out.add(3);
        assertDoesNotThrow(() -> out.remove(0));
        assertEquals(2, out.get(0));
        assertEquals(3, out.get(1));
        assertDoesNotThrow(() -> out.remove(1));
        assertEquals(2, out.get(0));
        assertThrows(IllegalArgumentException.class, () -> out.get(1));
    }

    @Test
    public void shouldRemoveByObjectCorrectly() {
        out.add(2);
        out.add(3);
        assertDoesNotThrow(() -> out.remove(1));
        assertEquals(1, out.get(0));
        assertEquals(3, out.get(1));
        assertDoesNotThrow(() -> out.remove(1));
        assertEquals(1, out.get(0));
        assertThrows(IllegalArgumentException.class, () -> out.get(1));
    }

    @Test
    public void shouldReturnCorrectContains() {
        out.add(2);
        out.add(3);
        assertTrue(out.contains(1));
        assertTrue(out.contains(1));
        assertTrue(out.contains(2));
        assertTrue(out.contains(2));
        assertTrue(out.contains(3));
        assertTrue(out.contains(3));
        assertFalse(out.contains(4));
        assertFalse(out.contains(0));
        assertFalse(out.contains(-1));
    }

    @Test
    public void shouldReturnCorrectIndex() {
        out.add(2);
        out.add(2);
        out.add(3);
        out.add(3);
        assertEquals(0, out.indexOf(1));
        assertEquals(0, out.lastIndexOf(1));
        assertEquals(1, out.indexOf(2));
        assertEquals(2, out.lastIndexOf(2));
        assertEquals(3, out.indexOf(3));
        assertEquals(4, out.lastIndexOf(3));
        assertEquals(-1, out.lastIndexOf(4));
        assertEquals(-1, out.indexOf(4));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, -1, 0})
    public void shouldReturnCorrectGet( Integer input ) {
        out.add(input);
        assertEquals(1, out.get(0));
        assertEquals(input, out.get(1));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, -1})
    public void shouldThrowIfElementDoesntExistRemove( Integer input ) {
        assertThrows(IllegalArgumentException.class, () -> out.remove(input));
    }

    @Test
    public void shouldClearCorrectly() {
        out.clear();
        assertThrows(IllegalArgumentException.class, () -> out.get(0));
        assertEquals(0, out.size());
        out.add(1);
        assertDoesNotThrow(() -> out.get(0));
        assertEquals(1, out.size());
        out.clear();
        assertThrows(IllegalArgumentException.class, () -> out.get(0));
        assertEquals(0, out.size());
    }

    @Test
    public void shouldCreateCorrectArray() {
        Integer[] arr = out.toArray();
        assertEquals(1, arr.length);
        assertEquals(out.size(), arr.length);
        assertEquals(out.get(0), arr[0]);
    }

}