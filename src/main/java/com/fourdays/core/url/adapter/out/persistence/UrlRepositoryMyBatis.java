package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.url.adapter.out.persistence.dto.ProtocolDto;
import com.fourdays.core.url.adapter.out.persistence.dto.UrlDto;
import com.fourdays.core.url.application.port.out.UrlRepository;
import com.fourdays.core.url.domain.Protocol;
import com.fourdays.core.url.domain.URL;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UrlRepositoryMyBatis implements UrlRepository {

    private final UrlMapper urlMapper;

    private final ProtocolMapper protocolMapper;

    @Override
    public URL save(URL url) {
        urlMapper.save(UrlDto.of(url));
        Protocol findProtocol = protocolMapper.findBySeq(url.getProtocol().getSeq())
                .toEntity();
        return urlMapper.findByUrlKey(url.getUrlKey())
                .toEntity(findProtocol);
    }

    @Override
    public Optional<URL> findByUrlKey(String urlKey) {
        UrlDto urlDto = urlMapper.findByUrlKey(urlKey);
        if (urlDto == null) {
            return Optional.empty();
        }

        ProtocolDto protocolDto = protocolMapper.findBySeq(urlDto.getProtocolSeq());
        if (protocolDto == null) {
            throw new IllegalStateException("ProtocolDto is null. urlDto=" + urlDto);
        }

        return Optional.ofNullable(urlDto.toEntity(protocolDto.toEntity()));
    }
}
