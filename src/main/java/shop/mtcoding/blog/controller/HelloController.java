package shop.mtcoding.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("username", "ssar");
        return "test";
    }

    @GetMapping("/array")
    public String test2(Model model) {
        List<String> list = new ArrayList<>();
        list.add("바나나");
        list.add("딸기");
        model.addAttribute("list", list);
        return "array";
    }

}
