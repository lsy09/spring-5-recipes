package com.apress.springrecipes.board;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;


/**
 * 7-5 메서드 호출 보안하기
 *
 * 빈 인터페이스나 구현 클래스에서 보안 대상 메서드에 @Secured, @PostAuthorize, @PreFilter/@PostFilter 등의 애너테이션을 붙여 선언
 * 구성 클래스 레벨에 @EnableGlobalMethodSecurity를 붙이면 보안 모드로 작동
 *
 * > 애너테이션을 붙여 메서드 보안하기
 * 메서드에 @Secured를 붙이면 보안이 적용됨, 각 메서드에 @Secured를 붙이고 String[]형 access 속성에 메서드별로 접근 허용 권한을 하나 이상 명시
 */
@Service
@Transactional
class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    @Secured("USER")
    public List<Todo> listTodos() {
        return todoRepository.findAll();
    }

    @Override
    @Secured("USER")
    public void save(Todo todo) {
        this.todoRepository.save(todo);
    }

    @Override
    @Secured("USER")
    public void complete(long id) {
        Todo todo = findById(id);
        todo.setCompleted(true);
        todoRepository.save(todo);
    }

    @Override
    @Secured({"USER", "ADMIN"})
    public void remove(long id) {
        todoRepository.remove(id);
    }

    @Override
    @Secured("USER")
    public Todo findById(long id) {
        return todoRepository.findOne(id);
    }
}

