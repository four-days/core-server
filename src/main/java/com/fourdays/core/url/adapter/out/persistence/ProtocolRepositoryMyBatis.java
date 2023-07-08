package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.url.adapter.out.persistence.dto.ProtocolDto;
import com.fourdays.core.url.application.port.out.ProtocolRepository;
import com.fourdays.core.url.domain.Protocol;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ProtocolRepositoryMyBatis implements ProtocolRepository {

    private final ProtocolMapper protocolMapper;

    @Override
    public Protocol save(Protocol protocol) {
        ProtocolDto protocolDto = ProtocolDto.of(protocol);
        protocolMapper.save(protocolDto);

        return protocolMapper.findByName(protocol.getName())
                .toEntity();
    }

    @Override
    public Optional<Protocol> findByName(String name) {
        ProtocolDto protocolDto = protocolMapper.findByName(name);
        if (protocolDto == null) {
            return Optional.empty();
        }

        return Optional.of(protocolDto.toEntity());
    }
}
