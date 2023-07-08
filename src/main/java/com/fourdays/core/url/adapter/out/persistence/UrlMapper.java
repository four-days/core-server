package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.url.adapter.out.persistence.dto.UrlDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UrlMapper {

    int save(UrlDto urlDto);

    UrlDto findByUrlKey(String urlKey);
}
