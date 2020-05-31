package com.sudria.demo.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Class {

  private Long id;
  private String name;
  private int number_of_students;
  private String head_teacher;
  private List<Student> students;

  @Getter
  @ToString
  @EqualsAndHashCode
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Student {

    private Long id;
    private String name;
    private int age;

  }

}
