package com.sudria.demo.application.graphql;

import com.sudria.demo.domain.Class;
import com.sudria.demo.domain.ClassService;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassResolver {

  private ClassService classService;

  public ClassResolver(ClassService classService) {
    this.classService = classService;
  }

  @GraphQLQuery
  public List<Class> getClasses (){
    return classService.getClasses();
  }

}
