package com.eli.urlshortener.base62tests;

import com.eli.urlshortener.exceptions.IllegalShortCodeException;
import com.eli.urlshortener.util.Base62;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTests {


    @Test
    @DisplayName("""
            GIVEN: An empty string
            WHEN: Base62 constructor is called
            THEN: An IllegalShortCodeException is thrown
            """)
    void testEmptyString(){
        assertThrows(IllegalShortCodeException.class, () -> new Base62(""));
    }

    @Test
    @DisplayName("""
            GIVEN: A String with invalid(non digit or letter chars)
            WHEN: Base62 constructor is called
            THEN: An IllegalShortCodeException is thrown
            """)
    void testIllegalString(){
        assertThrows(IllegalShortCodeException.class,
                () -> new Base62("jlasdnjakl-s2378103982"));

        assertThrows(IllegalShortCodeException.class,
                () -> new Base62("asdklas  sadjlkasdjas"));
    }

    @Test
    @DisplayName("""
            GIVEN: A String containing valid characters
            WHEN: Base62 constructor is called
            THEN: No exception is thrown
            """)
    void testLegalString(){
        String base62Dic = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        assertDoesNotThrow(() -> new Base62(base62Dic));
        assertEquals(base62Dic.length(), 62);
    }

    @Test
    @DisplayName("""
            GIVEN: A String containing valid characters with spaces in the beginning or end of the string
            WHEN: Base62 constructor is called
            THEN: No exception is thrown
            """
    )
    void testSpacedString(){
        String base62Dic = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Base62 base62num = new Base62(base62Dic);

        String base62DicStartSpace = "      0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        assertDoesNotThrow(() -> new Base62(base62DicStartSpace));
        assertEquals(base62num, new Base62(base62DicStartSpace));

        String base62DicEndSpace = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz       ";
        assertDoesNotThrow(() -> new Base62(base62DicEndSpace));
        assertEquals(base62num, new Base62(base62DicEndSpace));

        String base62DicBothSideSpace = "       0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz       ";
        assertDoesNotThrow(() -> new Base62(base62DicBothSideSpace));
        assertEquals(base62num, new Base62(base62DicBothSideSpace));
    }
}
