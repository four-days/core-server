package com.fourdays.core.url.adapter.out.persistence.dto;

import com.fourdays.core.url.domain.Protocol;

import java.util.Objects;

public class ProtocolDto {

    private final Integer seq;

    private final String name;

    public ProtocolDto(Integer seq, String name) {
        this.seq = seq;
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public Protocol toEntity() {
        return Protocol.builder()
                .seq(seq)
                .name(name)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolDto that = (ProtocolDto) o;
        return Objects.equals(getSeq(), that.getSeq()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSeq(), getName());
    }

    @Override
    public String toString() {
        return "ProtocolDto{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                '}';
    }
}
