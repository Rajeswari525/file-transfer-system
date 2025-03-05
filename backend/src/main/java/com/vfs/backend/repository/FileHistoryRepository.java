package com.vfs.backend.repository;

import com.vfs.backend.entities.FileHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileHistoryRepository extends JpaRepository<FileHistory, Long> {
}
