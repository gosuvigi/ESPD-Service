package eu.europa.ec.grow.espd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vigi on 10/2/2016.
 */
@RestController
class ApiController {

    @GetMapping("/api/test")
    List<String> apiTest() {
      return Arrays.asList("hodor", "HODOR", "Hodor??");
    }

}
