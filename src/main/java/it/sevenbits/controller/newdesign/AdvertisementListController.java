package it.sevenbits.controller.newdesign;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "new/advertisement")
public class AdvertisementListController {

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("list.jade");
    }
}
