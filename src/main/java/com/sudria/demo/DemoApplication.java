package com.sudria.demo;

import com.sudria.demo.domain.Class.Student;
import com.sudria.demo.infrastructure.ClassEntity;
import com.sudria.demo.infrastructure.SchoolRepository;
import com.sudria.demo.infrastructure.StudentEntity;
import com.sudria.demo.infrastructure.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

  private SchoolRepository schoolRepository;
  private StudentRepository studentRepository;

  public DemoApplication(SchoolRepository schoolRepository, StudentRepository studentRepository) {
    this.schoolRepository = schoolRepository;
    this.studentRepository = studentRepository;
  }

  public static void main(String[] args) {

    SpringApplication.run(DemoApplication.class, args);
    System.out.println("Hello SUDRIA !");
  }

  @Override
  public void run(String... args) {

    log.info("Data initilisation...");
    saveClass(1, "3ème A", 25, "Alexa Londeau", Arrays.asList(Student.builder().age(15).name("Carole Falaise").build()));
    saveClass(2, "6ème D", 32, "Patrick Dupont", Arrays.asList(Student.builder().age(12).name("Enzo Bounichou").build()));
  }

  @Transactional
  private void saveClass(long id, String name, int number_of_students, String head_teacher, List<Student> students) {

    ClassEntity classEntity = this.schoolRepository.save(
        ClassEntity
            .builder()
            .id(id)
            .name(name)
            .number_of_students(number_of_students)
            .head_teacher(head_teacher)
            .build());

    students.stream()
        .forEach(student ->
            studentRepository.save(
                StudentEntity
                    .builder()
                    .name(student.getName())
                    .age(student.getAge())
                    .classEntity(classEntity)
                    .build()
            ));
  }

}
