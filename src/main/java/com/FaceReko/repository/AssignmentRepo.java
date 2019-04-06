package com.FaceReko.repository;

import com.FaceReko.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepo extends JpaRepository<Assignment , Long> {
 public List<Assignment> findByBatch(String batch);

}

