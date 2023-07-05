package com.fourdays.core.url.application.port.in;

import com.fourdays.core.url.domain.Protocol;

import java.util.Optional;

public interface ProtocolService {

    Optional<Protocol> findByName(String name);
}
