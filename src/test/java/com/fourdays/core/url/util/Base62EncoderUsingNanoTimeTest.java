package com.fourdays.core.url.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Base62EncoderUsingNanoTimeTest {

    @Test
    @DisplayName("나노타임으로 인코딩을 할 수 있다.")
    void encodeTest_returnValueCheck() {
        Base62Encoder base62Encoder = new MockBase62EncoderUsingNanoTime();
        String encoded = base62Encoder.encode();
        assertThat(encoded).isEqualTo("aRazJc5c");
    }

    static class MockBase62EncoderUsingNanoTime extends Base62EncoderUsingNanoTime {

        @Override
        protected long getNanoTime() {
            return 92552159069250L;
        }
    }
}