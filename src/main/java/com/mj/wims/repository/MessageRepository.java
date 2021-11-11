package com.mj.wims.repository;

import com.mj.wims.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("UPDATE Message m SET m.read = 'true' WHERE m.id = ?1")
    void setMessageReadById(Long id);
}
