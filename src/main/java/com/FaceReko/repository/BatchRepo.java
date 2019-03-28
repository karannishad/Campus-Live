package com.FaceReko.repository;

import com.FaceReko.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepo extends JpaRepository<Batch,String> {
}
