package com.example.fsc.repository;

import com.example.fsc.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findBoardsByAuthorContainingOrderByCreatedAtDesc(String email);

}
