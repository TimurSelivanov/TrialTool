package com.trialtool.repository;

import com.trialtool.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsRepository extends JpaRepository<Tool, Integer> {
    List<Tool> findByTypeStartingWith(String title);
}

