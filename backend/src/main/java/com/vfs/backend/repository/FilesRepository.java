package com.vfs.backend.repository;

import com.vfs.backend.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesRepository extends JpaRepository<File, Long> {

    public Optional<File> findByFileName(String fileName);
}
