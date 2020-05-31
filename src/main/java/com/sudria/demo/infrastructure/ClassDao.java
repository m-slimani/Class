package com.sudria.demo.infrastructure;

import com.sudria.demo.domain.Class;
import com.sudria.demo.domain.Class.Student;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClassDao {

  private SchoolRepository schoolRepository;
  private StudentRepository studentRepository;

  public ClassDao(SchoolRepository schoolRepository, StudentRepository studentRepository) {
    this.schoolRepository = schoolRepository;
    this.studentRepository = studentRepository;
  }

  public List<Class> findClasses() {
    return StreamSupport.stream(schoolRepository.findAll().spliterator(), false)
        .map(classEntitie -> buildClass(classEntitie, studentRepository.findByClassEntity(classEntitie)))
        .collect(Collectors.toList());
  }

  public Class findClasses(Long id) throws NotFoundException {
    ClassEntity classEntity = schoolRepository.findById(id).orElseThrow(NotFoundException::new);
    return buildClass(classEntity, studentRepository.findByClassEntity(classEntity));
  }

  public Class createClasses(Class aClass) throws NotFoundException {
    ClassEntity classEntity = schoolRepository.save(buildClassEntity(aClass));

    aClass.getStudents()
        .stream()
        .forEach(student ->
            studentRepository.save(buildStudentEntity(classEntity, student)));

    return buildClass(
        schoolRepository.findById(classEntity.getId()).orElseThrow(NotFoundException::new),
        studentRepository.findByClassEntity(classEntity));
  }


  public void deleteClasses(Long id) {
    schoolRepository.delete(schoolRepository.findById(id).get());
  }

  public void updateClass(Class aClass) {

    ClassEntity classEntity = schoolRepository.save(buildClassEntity(aClass));

    aClass.getStudents()
        .stream()
        .forEach(student ->
            studentRepository.save(buildStudentEntity(classEntity, student)));
  }

  private StudentEntity buildStudentEntity(ClassEntity classEntity, Student student) {
    return StudentEntity.builder()
        .name(student.getName())
        .age(student.getAge())
        .classEntity(classEntity)
        .build();
  }

  public Class replaceClass(Class aClass) {
    ClassEntity classEntity = schoolRepository.save(buildClassEntity(aClass));
    return buildClass(classEntity,  studentRepository.findByClassEntity(classEntity));
  }

  private ClassEntity buildClassEntity(Class aClass) {
    return ClassEntity
        .builder()
        .name(aClass.getName())
        .number_of_students(aClass.getNumber_of_students())
        .head_teacher(aClass.getHead_teacher())
        .build();
  }

  private Class buildClass(ClassEntity classEntity, List<StudentEntity> studentEntities) {
    return Class.builder()
        .id(classEntity.getId())
        .name(classEntity.getName())
        .number_of_students(classEntity.getNumber_of_students())
        .head_teacher(classEntity.getHead_teacher())
        .students(
            studentEntities
                .stream()
                .map(studentEntity -> Student.builder()
                    .id(studentEntity.getId())
                    .age(studentEntity.getAge())
                    .name(studentEntity.getName())
                    .build())
                .collect(Collectors.toList())
        )
        .build();
  }


}
