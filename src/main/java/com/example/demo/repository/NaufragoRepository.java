package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Naufrago;

import java.util.List;

@Repository
public interface NaufragoRepository extends JpaRepository<Naufrago, Long> {
    @Query("select distinct n from Naufrago n left join fetch n.habilidads")
    List<Naufrago> findAllWithHabilidads();
}