package com.sudria.demo.application;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

  @Autowired
  private MockMvc mockMvc;


  @Autowired
  private Controller controller;

  @Test
  public void contexLoads() {
    Assertions.assertThat(controller).isNotNull();
  }

  @Test
  public void getAnimals() throws Exception {

    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/animals"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers
            .content()
            .json(
                Stream
                    .of("cat", "dog", "bird")
                    .collect(Collectors.toList()).toString()));
  }

}
