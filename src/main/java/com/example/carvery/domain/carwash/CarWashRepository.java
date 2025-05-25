package com.example.carvery.domain.carwash;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarWashRepository extends JpaRepository<CarWashInfo, Integer> {
}
