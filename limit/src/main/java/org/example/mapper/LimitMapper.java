package org.example.mapper;

import org.example.dto.LimitDto;
import org.example.model.LimitEntity;
import org.springframework.stereotype.Component;

@Component
public class LimitMapper {
    public LimitDto limitEntityToLimitDto(LimitEntity limitEntity) {
        return new LimitDto(limitEntity.getId(), limitEntity.getUserId(), limitEntity.getLimitAmount());
    }
}
