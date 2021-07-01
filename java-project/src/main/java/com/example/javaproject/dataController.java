package com.example.javaproject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class dataController {
@RequestMapping(method = RequestMethod.GET,path = "/hello")
  public String helloWorld(){
    SmileDemo s = new SmileDemo() ;

      return  "sad";
  }
}
