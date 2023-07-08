package com.fourdays.core.url.domain;

import lombok.Builder;

import java.util.Objects;

public class Protocol {

    private final Integer seq;

    private final String name;

    @Builder
    public Protocol(Integer seq, String name) {
        this.seq = seq;
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol = (Protocol) o;
        return Objects.equals(getSeq(), protocol.getSeq()) && Objects.equals(getName(), protocol.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeq(), getName());
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                '}';
    }
}
