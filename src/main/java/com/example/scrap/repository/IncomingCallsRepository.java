package com.example.scrap.repository;

import com.example.scrap.model.Id;
import com.example.scrap.model.IncomingCallsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncomingCallsRepository extends JpaRepository<IncomingCallsDetails, Id> {

    @Query("Select u from IncomingCallsDetails u where u.id.server like ?1 and u.id.date < ?2 ORDER BY u.id.date DESC")
    List<IncomingCallsDetails> findLastData(@Param("server") String server, @Param("currentDate") LocalDateTime currentDate, Pageable pageable);
}
