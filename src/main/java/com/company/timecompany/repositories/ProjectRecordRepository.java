package com.company.timecompany.repositories;

import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRecordRepository extends JpaRepository<ProjectRecord, Integer> {

    @Query("SELECT pr FROM ProjectRecord pr WHERE pr.project.user.firstName LIKE %:keyword% OR pr.summary LIKE " +
            "%:keyword%")
    List<ProjectRecord> searchAll(@Param("keyword") String keyword);
    @Query("SELECT pr FROM ProjectRecord pr WHERE WEEK(DATE(pr.date)) = :weekNumber")
    List<ProjectRecord> findByWeekNumber(@Param("weekNumber") Integer weekNumber);
    List<ProjectRecord> findByProjectUser(User projectUser);
    @Query("SELECT pr FROM ProjectRecord pr WHERE pr.project.user = :projectUser AND (pr.project.user.firstName LIKE " +
            "%:keyword% OR pr.summary LIKE %:keyword%)")
    List<ProjectRecord> searchByProjectUser(@Param("projectUser") User projectUser, @Param("keyword") String keyword);

    @Query("SELECT pr FROM ProjectRecord pr WHERE pr.project.user = :projectUser AND WEEK(DATE(pr.date)) = :weekNumber")
    List<ProjectRecord> findByWeekNumberForProjectUser(@Param("weekNumber") Integer weekNumber, @Param("projectUser") User projectUser);
}
