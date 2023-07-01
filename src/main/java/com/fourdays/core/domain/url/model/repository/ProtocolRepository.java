package com.fourdays.core.domain.url.model.repository;

import com.fourdays.core.domain.url.model.entity.Protocol;

import java.util.Optional;

public interface ProtocolRepository {

    Optional<Protocol> findByName(String name);
}
