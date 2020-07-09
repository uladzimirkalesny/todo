package learn.spring.todo.controller;

import learn.spring.todo.domain.ToDo;
import learn.spring.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class ToDoController {

    private final ToDoRepository repository;

    @Autowired
    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(
            value = "/toDos",
            method = {RequestMethod.GET},
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.TEXT_XML_VALUE
            }
    )
    public ResponseEntity<Iterable<ToDo>> getToDos(@RequestHeader HttpHeaders headers) {
        return new ResponseEntity<Iterable<ToDo>>(this.repository.findAll(), headers, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/delete/{id}",
            method = {RequestMethod.DELETE},
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.TEXT_XML_VALUE
            }
    )
    public ResponseEntity<Iterable<ToDo>> delete(@PathVariable String id,
                                                 @RequestHeader HttpHeaders headers) {
        this.repository.delete(id);
        return new ResponseEntity<Iterable<ToDo>>(this.repository.findAll(), headers, HttpStatus.OK);
    }

}
