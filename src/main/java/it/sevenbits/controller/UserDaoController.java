package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserDaoController {

    @RequestMapping(value = "userdao.html", method = RequestMethod.GET)
    public ModelAndView helloWorld() {

        //Создаем вьюшку по userDao.jsp, которая выведется этим контроллером на экран
        ModelAndView users = new ModelAndView("userDao");
        return users;
    }

}
