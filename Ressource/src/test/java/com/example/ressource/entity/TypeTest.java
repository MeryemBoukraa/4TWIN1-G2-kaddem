package com.example.ressource.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void testEnumValues() {
        Type[] values = Type.values();
        assertEquals(3, values.length);
        assertArrayEquals(new Type[]{Type.E_Book, Type.Cours, Type.Article}, values);
    }

    @Test
    void testEnumValueOf() {
        assertEquals(Type.E_Book, Type.valueOf("E_Book"));
        assertEquals(Type.Cours, Type.valueOf("Cours"));
        assertEquals(Type.Article, Type.valueOf("Article"));
    }
}
