package com.sudria.demo.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="STUDENT_ENTITY")
public class StudentEntity {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "AGE", length = 50, nullable = false)
  private int age;
  @Column(name = "NAME", length = 50, nullable = false)
  private String name;

  @ManyToOne
  private ClassEntity classEntity;
}
