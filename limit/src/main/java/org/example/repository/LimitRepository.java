package org.example.repository;

import org.example.model.LimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<LimitEntity, Long> {
    Optional<LimitEntity> findByUserId(Long userId);

    @Modifying
    @Query("update LimitEntity le set le.limitAmount=:limitAmount")
    void resetDailyLimits(@Param("limitAmount") BigDecimal limitAmount);
}
