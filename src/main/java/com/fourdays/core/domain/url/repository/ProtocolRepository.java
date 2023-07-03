package com.fourdays.core.domain.url.repository;

import com.fourdays.core.domain.url.entity.Protocol;

import java.util.Optional;

public interface ProtocolRepository {

    Optional<Protocol> findByName(String name);
}
