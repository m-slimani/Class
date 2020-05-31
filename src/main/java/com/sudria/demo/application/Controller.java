package com.sudria.demo.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sudria.demo.domain.Class;
import com.sudria.demo.domain.ClassService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api("Apis de gestin de lycée")
public class Controller {

  private ClassService classService;
  private ObjectMapper objectMapper;

  public Controller(ClassService classService, ObjectMapper objectMapper) {
    this.classService = classService;
    this.objectMapper = objectMapper;
  }


  @ApiOperation(value = "View a list of available classes", response = List.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Successfully retrieved list"),
      @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
      @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
      @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
  })
  @RequestMapping(value = "/classes", method = RequestMethod.GET)
  @CrossOrigin(origins = "http://localhost:4200")
  public ResponseEntity<List<Class>> getClasses() {
    return new ResponseEntity<>(classService.getClasses(), HttpStatus.OK);
  }

  @RequestMapping(value = "/classes/{id}", method = RequestMethod.GET)
  public ResponseEntity<Class> getClassesById(@PathVariable(value = "id") Long id) {
    try {
      log.info("********************** inside the controller ****************************");
      return new ResponseEntity<>(classService.getClasses(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class Not Found", e);
    }
  }

  @RequestMapping(value = "/classes", method = RequestMethod.POST)
  public ResponseEntity<Class> createClasses(
      @ApiParam(value = "Class object stored in database table", required = true)
      @RequestBody Class aClass) throws NotFoundException {
    aClass = classService.addClass(aClass);
    return new ResponseEntity<>(aClass, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/classes/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Class> replaceClasses(
      @PathVariable(value = "id") Long id,
      @RequestBody Class aClass) {
    aClass.setId(id);
    aClass = classService.replaceClass(aClass);
    return new ResponseEntity<>(aClass, HttpStatus.OK);
  }

  @RequestMapping(value = "/classes/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Class> deleteClasses(@PathVariable(value = "id") Long id) {
    classService.deleteClasses(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/classes/{id}", method = RequestMethod.PATCH, consumes = "application/json-patch+json")
  public ResponseEntity<String> patchClasses(
      @PathVariable(value = "id") Long id,
      @RequestBody JsonPatch patch)  {
    try {
      classService.patchClasses(applyPatchToCustomer(patch, classService.findClass(id)));
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (JsonPatchException | JsonProcessingException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private Class applyPatchToCustomer(JsonPatch patch, Class targetClass)
      throws JsonPatchException, JsonProcessingException {
    JsonNode patched = patch.apply(objectMapper.convertValue(targetClass, JsonNode.class));
    return objectMapper.treeToValue(patched, Class.class);
  }
}
