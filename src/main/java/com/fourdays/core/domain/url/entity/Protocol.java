package com.fourdays.core.domain.url.entity;

public class Protocol {

    private final Integer seq;

    private final String name;

    public Protocol(Integer seq, String name) {
        this.seq = seq;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isHttp() {
        return "HTTP".equalsIgnoreCase(this.name);
    }

    public boolean isHttps() {
        return "HTTPS".equalsIgnoreCase(this.name);
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                '}';
    }
}
