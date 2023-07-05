package com.fourdays.core.url.application.port.out;

import com.fourdays.core.url.domain.Protocol;

import java.util.Optional;

public interface ProtocolRepository {

    Optional<Protocol> findByName(String name);
}
