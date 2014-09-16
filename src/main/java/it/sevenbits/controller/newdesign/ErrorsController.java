package it.sevenbits.controller.newdesign;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorsController {

    @RequestMapping(value = "/400.html", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView error400() {
        return new ModelAndView("../400.jade");
    }

    @RequestMapping(value = "/403.html", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView error403() {
        return new ModelAndView("../403.jade");
    }

    @RequestMapping(value = "/404.html", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView error404() {
        return new ModelAndView("../404.jade");
    }

    @RequestMapping(value = "/500.html", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView error500() {
        return new ModelAndView("../500.jade");
    }
}
