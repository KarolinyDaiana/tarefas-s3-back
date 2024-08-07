package br.senai.sc.demo.controller;

import br.senai.sc.demo.controller.dto.ResponseTaskDto;
import br.senai.sc.demo.controller.dto.TaskDto;
import br.senai.sc.demo.model.Task;
import br.senai.sc.demo.service.TaskServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin("*")
public class TaskController {

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }
    private final TaskServiceImpl taskService;

    @PostMapping("/novatask")
    public ResponseEntity<Task> criarTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.criarTask(taskDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> verTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.verTask(id));
    }

    @GetMapping()
    public ResponseEntity<List<Task>> verTask() {
        return ResponseEntity.ok(taskService.verTodas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.excluirTask(id));
    }
}
