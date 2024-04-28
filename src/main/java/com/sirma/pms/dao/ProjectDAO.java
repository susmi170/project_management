package com.sirma.pms.dao;

import com.sirma.pms.entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDAO extends CrudRepository<Project, Integer> {
}
