package com.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {
    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        System.out.println("test方法执行了");
        return "success";
    }
}
