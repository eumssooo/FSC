package com.example.fsc.repository;


import com.example.fsc.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity , Long> {
        List<CommentEntity> findByPostIdOrderByCreatedAtAsc(Long post_id);

}
