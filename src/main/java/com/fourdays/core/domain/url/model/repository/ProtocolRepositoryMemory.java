package com.fourdays.core.domain.url.model.repository;

import com.fourdays.core.domain.url.model.entity.Protocol;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProtocolRepositoryMemory implements ProtocolRepository {

    private final Map<String, Protocol> storeByName;

    public ProtocolRepositoryMemory() {
        this.storeByName = new HashMap<>(){{
            put("HTTP", new Protocol(1, "HTTP"));
            put("HTTPS", new Protocol(2, "HTTPS"));
        }};
    }

    @Override
    public Optional<Protocol> findByName(String name) {
        return Optional.ofNullable(storeByName.get(name.toUpperCase()));
    }
}
