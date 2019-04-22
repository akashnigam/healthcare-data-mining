package com.swm.searchInterface.helloworld;

import com.swm.searchInterface.entity.Disease;
import com.swm.searchInterface.entity.Symptom;
import com.swm.searchInterface.repositories.DiseaseRepository;
import com.swm.searchInterface.repositories.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;
    //private MessageSource resourceBundleMessageSource;
    //GTE
    //URI - /hello world
    //method
   // @RequestMapping(method = RequestMethod.GET, path = "/hello_world")

    @Autowired
    public DiseaseRepository diseaseRepository;

    @Autowired
    public EntityManager entityManager;

    @Autowired
    public SymptomRepository symptomRepository;

    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "hello world";
    }


    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello world bean","vinay");
    }


    @GetMapping(path = "/diseaseNames")
    public List<String> diseaseNames(){
        return diseaseRepository.findDiseaseNames();
    }


    @GetMapping(path = "/all-diseases")

    public List<Disease> getDiseases(){
        System.out.println(diseaseRepository.findDisease(new int[]{1,2}));
        return diseaseRepository.findAll();
    }


    @GetMapping(path = "/hello-world-bean/path-variable/{name}" )
    public HelloWorldBean helloWorldBeanPathVaraible(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s",name),"nani");
    }


    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalize(@RequestHeader(name = "Accept-Language",required=false) Locale locale){
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
    }


}
