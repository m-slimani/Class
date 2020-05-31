package com.sudria.demo.domain;

import com.sudria.demo.infrastructure.ClassDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClassService {

  private ClassDao classDao;

  public ClassService(ClassDao classDao) {
    this.classDao = classDao;
  }

  public List<Class> getClasses() {
    return classDao.findClasses();
  }

  @Cacheable("classes")
  public Class getClasses(Long id) throws NotFoundException {
    log.info("********************** inside the AnimalService ****************************");
    return classDao.findClasses(id);
  }

  public Class addClass(Class aClass) throws NotFoundException {
    return classDao.createClasses(aClass);
  }

  public void deleteClasses(Long id) {
    classDao.deleteClasses(id);
  }

  public void patchClasses(Class aClass) {
    classDao.updateClass(aClass);
  }

  public Class findClass(Long id) throws NotFoundException {
    return classDao.findClasses(id);
  }

  public Class replaceClass(Class aClass) {
    return classDao.replaceClass(aClass);
  }
}
