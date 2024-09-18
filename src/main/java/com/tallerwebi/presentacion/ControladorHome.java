package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorHome {

    @RequestMapping(path = "home")
    public ModelAndView irAlHome(){
        return new ModelAndView("miHome");
    }
}
