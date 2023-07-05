package com.fourdays.core.url.application.service;

import com.fourdays.core.url.application.port.in.ProtocolService;
import com.fourdays.core.url.domain.Protocol;
import com.fourdays.core.url.application.port.out.ProtocolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;

    @Override
    public Optional<Protocol> findByName(String name) {
        return protocolRepository.findByName(name);
    }
}
