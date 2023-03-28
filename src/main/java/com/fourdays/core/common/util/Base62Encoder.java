package com.fourdays.core.common.util;

public class Base62Encoder {

    private static final char[] CODES = {
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9',
    };

    private static final int SIZE = CODES.length;

    private Base62Encoder() {
    }

    public static String encode(long number) {
        if (number < 0L) {
            throw new IllegalArgumentException("number is inappropriate.");
        }

        long sequence = number;

        StringBuilder sb = new StringBuilder();
        while (sequence >= SIZE) {
            sb.append(CODES[(int) (sequence % SIZE)]);
            sequence /= SIZE;
        }

        sb.append(CODES[(int) (sequence % SIZE)]);

        return sb.reverse().toString();
    }
}
