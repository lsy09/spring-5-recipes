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
 * > 아시지 시큐리티(Acegi Security) 프로젝트로 시작된 스프링 시큐리티는 스프링 포트폴리오 프로젝트에 병합 되면서 이름을 변경
 *
 * > '인증(Authentication)은 주체(principal)의 신원(identity)를 증명하는 과정입니다.'는 주장을 검증하는 과정
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
