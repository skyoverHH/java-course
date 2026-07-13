package org.example.repository;

import org.example.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "select * from products p where p.user_id = :id", nativeQuery = true)
    List<ProductEntity> findAllByUserId(@Param("id") Long id);

    Optional<ProductEntity> findByAccountNumber(String accountNumber);
}
