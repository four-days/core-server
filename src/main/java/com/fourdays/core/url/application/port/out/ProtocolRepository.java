package com.fourdays.core.url.application.port.out;

import com.fourdays.core.url.domain.Protocol;

import java.util.Optional;

public interface ProtocolRepository {

    Protocol save(Protocol protocol);

    Optional<Protocol> findByName(String name);
}
