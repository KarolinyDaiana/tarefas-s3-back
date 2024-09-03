package br.senai.sc.demo.controller;

import br.senai.sc.demo.controller.dto.TaskDto;
import br.senai.sc.demo.model.Task;
import br.senai.sc.demo.service.KafkaListener;
import br.senai.sc.demo.service.TaskServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
@CrossOrigin("*")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final KafkaListener kafkaListener;

    private List<TaskDto> taskMessages = new ArrayList<>();


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

    @org.springframework.kafka.annotation.KafkaListener(topics = "topico_novo_karol")
//    public void listen(TaskDto taskDto) {
//        System.out.println("Mensagem recebida: " + taskDto.nome());
//    }
    public void listen(TaskDto taskDto) {
        System.out.println("Task criada: " + taskDto);
        taskMessages.add(taskDto); // Armazena a mensagem na lista
    }

    @GetMapping("/messages")
    public List<TaskDto> getKafkaMessages() {
//        List<TaskDto> messages = new ArrayList<>(taskMessages);
        return taskMessages;
//        taskMessages.clear();  // Limpa a fila após retornar as mensagens
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> excluirTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.excluirTask(id));
    }
}
