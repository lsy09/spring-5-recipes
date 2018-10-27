package com.apress.springrecipes.board.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apress.springrecipes.board.Todo;
import com.apress.springrecipes.board.TodoService;

/**
 * 7. 스프링 시큐리티
 *
 * 아시지 시큐리티(Acegi Security) 프로젝트로 시작된 스프링 시큐리티는 스프링 포트폴리오 프로젝트에 병합 되면서 이름을 변경
 *
 * * '인증(Authentication)은 주체(principal)의 신원(identity)를 증명하는 과정입니다.'는 주장을 검증하는 과정
 * 주체란 유저, 기기, 시스템 등이 될 수 있지만 보통 유조(사용자)를 의미
 * 주체는 자신을 인증해달라고 신원 증명 정보, 즉 크레덴셜(credential)을 제시, 주체가 유저일 경우 크레덴셜은 대게 패스워드
 *
 * * 인가(Authorization)(권한부여)는 인증을 마친 유저에게 권한(authority)을 부여, 대상 애플리케이션의 특정 리소스에 접근할 수 있게 허가 하는 과정
 * 인가는 반드시 인증 과정 이후 수행 돼야 하며 권한은 홓 형태로 부여하는게 일반적
 *
 * * 접근통제(Access control)(접근제어)는 애플리케이션 리소스에 접근하는 행위를 제어, 어떤 유저가 어떤 리소스에 접근하도록 허락할지를 결정하는 행위
 * 즉, 접근 통제 결정(access control decision)(접근 제어 결정)
 * 리소스의 접근 속성과 유저에 부여된 권한 또는 다른 속성들을 견주어 결정
 */

@Controller
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public String list(Model model) {
        List<Todo> todos = todoService.listTodos();
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("todo", new Todo());
        return "todo-create";
    }

    @PostMapping
    public String newMessage(@ModelAttribute @Valid Todo todo, BindingResult errors) {

        if (errors.hasErrors()) {
            return "todo-create";
        }
        String owner = "marten@apressmedia.net";
        todo.setOwner(owner);
        todoService.save(todo);
        return "redirect:/todos";
    }

    @PutMapping("/{todoId}/completed")
    public String complete(@PathVariable("todoId") long todoId) {
        this.todoService.complete(todoId);
        return "redirect:/todos";
    }


    @DeleteMapping("/{todoId}")
    public String delete(@PathVariable("todoId") long todoId) {
        this.todoService.remove(todoId);
        return "redirect:/todos";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // We don't want to bind the id and owner fields as we control them in this controller and service instead.
        binder.setDisallowedFields("id", "owner");
    }

}
