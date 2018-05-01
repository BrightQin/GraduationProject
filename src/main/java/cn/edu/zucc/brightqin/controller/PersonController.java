package cn.edu.zucc.brightqin.controller;

import cn.edu.zucc.brightqin.entity.Person;
import cn.edu.zucc.brightqin.service.PersonService;
import cn.edu.zucc.brightqin.utils.PasswordUtil;
import cn.edu.zucc.brightqin.utils.PersonXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * controller
 *
 * @author brightqin
 */
@Controller
@RequestMapping(value = "/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * 保存添加的数据
     *
     * @param person person
     * @return personSavePage|redirect:main
     */
    @RequestMapping(value = "/savePerson", method = RequestMethod.POST)
    public String savePerson(@Valid Person person, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                map.put("ERROR_" + error.getField(), error.getDefaultMessage());
                System.out.println(error.getField() + "*" + error.getDefaultMessage());
            }
            return "personSavePage";
        } else {
            person.setPassword(PasswordUtil.MD5(person.getPassword()));
            personService.addPerson(person);
            return "redirect:main";
        }
    }

    /**
     * 跳转到添加页面
     *
     * @return personSavePage.jsp
     */
    @RequestMapping(value = "/addPerson")
    public String savePerson() {
        return "personSavePage";
    }

    /**
     * 删除一条数据
     */
    @RequestMapping(value = "/deletePersonById")
    public void deletePersonById(HttpServletResponse response, HttpServletRequest request) {
        String id = request.getParameter("id");
        personService.deletePersonById(Integer.valueOf(id));
    }


    /**
     * 跳转到更新页面，回显数据
     * personEditPage.jsp
     *
     * @param id    ID
     * @param model 使用的Model保存回显数据
     * @return personEditPage
     */
    @RequestMapping(value = "/doUpdate")
    public String doUpdate(@RequestParam(value = "id") String id, Model model) {
        model.addAttribute("person", personService.getPersonById(Integer.valueOf(id)));
        return "personEditPage";
    }

    /**
     * 更新数据
     *
     * @param person person
     * @return personEditPage|redirect:main
     */
    @RequestMapping(value = "/updatePerson")
    public String updatePerson(@Valid Person person, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                map.put("ERROR_" + error.getField(), error.getDefaultMessage());
                System.out.println(error.getField() + "*" + error.getDefaultMessage());
            }
            return "personEditPage";
        } else {
            if (personService.getPersonById(person.getId()).getPassword().equals(person.getPassword())) {
                personService.updatePerson(person);
            } else {
                person.setPassword(PasswordUtil.MD5(person.getPassword()));
                personService.updatePerson(person);
            }
            return "redirect:main";
        }
    }

    @RequestMapping(value = "/getPeople")
    public void getPeople(HttpServletResponse response, HttpServletRequest request) {
        String id = request.getParameter("id");
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = null;
        List<Person> people = personService.getPersonByDepartmentId(Integer.valueOf(id));
        try {
            if (people != null) {
                PersonXml personXML = new PersonXml(people);
                pw = response.getWriter();
                pw.print(personXML.build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
