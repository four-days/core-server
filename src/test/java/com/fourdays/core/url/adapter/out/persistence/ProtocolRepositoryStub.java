package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.url.application.port.out.ProtocolRepository;
import com.fourdays.core.url.domain.Protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProtocolRepositoryStub implements ProtocolRepository {

    private final Map<String, Protocol> storeByName;

    public ProtocolRepositoryStub() {
        this.storeByName = new HashMap<>() {{
            put("HTTP", new Protocol(1, "HTTP"));
            put("HTTPS", new Protocol(2, "HTTPS"));
        }};
    }

    @Override
    public Optional<Protocol> findByName(String name) {
        return Optional.ofNullable(storeByName.get(name.toUpperCase()));
    }
}
