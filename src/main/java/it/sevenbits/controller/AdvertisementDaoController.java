package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class AdvertisementDaoController {

    @Resource(name = "advertisementDao")
    private AdvertisementDao advertisementDao;

    @RequestMapping(value = "adv.html", method = RequestMethod.GET)
    public ModelAndView list() {

        //Создаем вьюшку по advertisement.jsp, которая выведется этим контроллером на экран
        ModelAndView modelAndView = new ModelAndView("advertisement");
        modelAndView.addObject("userName", "Vasya");
        List<Advertisement> advertisements = this.advertisementDao.findAll();
        modelAndView.addObject("advertisements", advertisements);

        return modelAndView;
    }

}
