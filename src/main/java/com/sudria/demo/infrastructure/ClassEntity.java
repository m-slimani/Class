package com.sudria.demo.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="CLASS_ENTITY")
public class ClassEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "NAME", length = 50, nullable = false)
  private String name;
  @Column(name = "NUMBER_OF_STUDENTS", nullable = false)
  private int number_of_students;
  @Column(name = "HEAD_TEACHER", length = 50, nullable = false)
  private String head_teacher;

  @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
  private List<StudentEntity> studentEntities;

}
