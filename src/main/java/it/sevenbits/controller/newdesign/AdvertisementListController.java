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

    @RequestMapping(value = "/enter.html", method = RequestMethod.GET)
    public ModelAndView enter() {
        return new ModelAndView("enter.jade");
    }

    @RequestMapping(value = "/registration.html", method = RequestMethod.GET)
    public ModelAndView registration() {
        return new ModelAndView("registration.jade");
    }

    @RequestMapping(value = "/view.html", method = RequestMethod.GET)
    public ModelAndView showAdvertisement() {
        return new ModelAndView("view.jade");
    }
}
