package com.mj.wims.repository;

import com.mj.wims.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    void setMessageReadById(Long id);
}
