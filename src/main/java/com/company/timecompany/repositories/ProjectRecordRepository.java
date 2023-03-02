package com.company.timecompany.repositories;

import com.company.timecompany.entities.ProjectRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRecordRepository extends JpaRepository<ProjectRecord, Integer> {

//    @Query("SELECT pr FROM ProjectRecord pr WHERE pr.status LIKE %:keyword% OR pr.summary LIKE %:keyword% OR WEEK(DATE(pr.date)) = :weekNumber")
//    List<ProjectRecord> searchAll(@Param("keyword") String keyword, @Param("weekNumber") Integer weekNumber);

    @Query("SELECT pr FROM ProjectRecord pr WHERE pr.status LIKE %:keyword% OR pr.summary LIKE %:keyword%")
    List<ProjectRecord> searchAll(@Param("keyword") String keyword);
    @Query("SELECT pr FROM ProjectRecord pr WHERE WEEK(DATE(pr.date)) = :weekNumber")
    List<ProjectRecord> findByWeekNumber(@Param("weekNumber") Integer weekNumber);
}
