package com.example.ressource.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void testEnumValues() {
        Type[] values = Type.values();
        assertEquals(3, values.length);
        assertArrayEquals(new Type[]{Type.E_BOOK, Type.COURS, Type.ARTICLE}, values);
    }

    @Test
    void testEnumValueOf() {
        assertEquals(Type.E_BOOK, Type.valueOf("E_BOOK"));
        assertEquals(Type.COURS, Type.valueOf("COURS"));
        assertEquals(Type.ARTICLE, Type.valueOf("ARTICLE"));
    }
}
