package com.FaceReko.repository;

import com.FaceReko.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {

public Student findByEnrollId(String enrollId);
public Student findByEnrollIdAndPassword(String enrollId,String Password);
}
