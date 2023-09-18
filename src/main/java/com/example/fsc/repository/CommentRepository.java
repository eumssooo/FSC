package com.example.fsc.repository;


import com.example.fsc.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity , Long> {


}
