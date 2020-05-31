package com.sudria.demo.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

 List<StudentEntity> findByClassEntityId(Long classEntityId);
 List<StudentEntity> findByClassEntity(ClassEntity classEntity);

}
