package it.sevenbits.controller.newdesign;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "new")
public class MainControler {


    @RequestMapping(value = "/main.html", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        return new ModelAndView("main.jade");
    }

}
