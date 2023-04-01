package com.fourdays.core.domain.url.util;

import org.springframework.stereotype.Component;

@Component
public class Base62EncoderUsingNanoTime implements Base62Encoder {

    @Override
    public String encode() {
        long sequence = getNanoTime();

        StringBuilder sb = new StringBuilder();
        while (sequence >= SIZE) {
            sb.append(CODES[(int) (sequence % SIZE)]);
            sequence /= SIZE;
        }

        sb.append(CODES[(int) (sequence % SIZE)]);

        return sb.reverse().toString();
    }

    protected long getNanoTime() {
        return System.nanoTime();
    }
}
