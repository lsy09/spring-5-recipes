package com.apress.springrecipes.board;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Transactional
class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * > 애너테이션 + 표현식으로 거르기
     * @PreAuthorize/@PostAuthorize 이외에 @PreFilter, @PostFilter도 있음
     * (@*Authorize)는 보안 규칙에 맞지 않을 경우 예외를 던지지만 (@*Filter)는 단순히 접근 권한이 없는 요소의 입출력 변수만 필터링
     */
    @Override
    @PreAuthorize("hasAuthority('USER')")
    @PostFilter("hasAnyAuthority('ADMIN') or filterObject.owner == authentication.name")
    public List<Todo> listTodos() {
        return todoRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public void save(Todo todo) {
        this.todoRepository.save(todo);
    }

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public void complete(long id) {
        Todo todo = findById(id);
        todo.setCompleted(true);
        todoRepository.save(todo);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void remove(long id) {
        todoRepository.remove(id);
    }

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public Todo findById(long id) {
        return todoRepository.findOne(id);
    }
}

