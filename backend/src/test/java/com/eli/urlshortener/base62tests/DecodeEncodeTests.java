package com.eli.urlshortener.base62tests;

import com.eli.urlshortener.util.Base62;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecodeEncodeTests {

    @Test
    @DisplayName("""
            GIVEN: a Long of value -1
            WHEN: Base62.encode is called with the value -1
            THEN: An ArithmeticException is thrown
            """)
    void testNegative(){
        assertThrows(ArithmeticException.class, () -> Base62.encode(-1L));
    }

    @Test
    @DisplayName("""
            GIVEN: a Long of value 0
            WHEN: Base62.encode is called with the value 0
            THEN: A Base62 object with the a string of 0 is returned
            """)
    void testZero(){
        Base62 base62 = Base62.encode(0L);
        assertEquals("0", base62.base62Num());
        assertEquals(0L, base62.decode());
    }

    @Test
    @DisplayName("""
            GIVEN: a Long of value bigger than zero
            WHEN: Base62.encode is called with the value 0
            THEN: A Base62 object with the a string of 0 is returned
            """)
    void testBiggerThanZero(){
        Base62 base62 = Base62.encode(1L);
        assertEquals("1", base62.base62Num());
        assertEquals(1L, base62.decode());
    }
}
