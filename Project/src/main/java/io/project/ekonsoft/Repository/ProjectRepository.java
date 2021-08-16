package io.project.ekonsoft.Repository;

import io.project.ekonsoft.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}

