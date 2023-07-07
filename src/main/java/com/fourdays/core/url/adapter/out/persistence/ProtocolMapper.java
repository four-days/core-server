package com.fourdays.core.url.adapter.out.persistence;

import com.fourdays.core.url.adapter.out.persistence.dto.ProtocolDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProtocolMapper {

    int save(String name);

    ProtocolDto findBySeq(Integer seq);

    ProtocolDto findByName(String name);
}
