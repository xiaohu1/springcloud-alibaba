package com.test.sentinelfeignConsumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

   @GetMapping("/feignTest")
    public String sentinelFeignTest(){
       return "sentinelfeign";
   }
}
