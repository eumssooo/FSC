package com.example.fsc.repository;

import com.example.fsc.entity.boardEntity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findBoardsByAuthorContainingOrderByCreatedAtDesc(String email);

}
