package com.example.fsc.repository;

import com.example.fsc.entity.memberEntity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
