package br.senai.sc.demo.service;

import br.senai.sc.demo.controller.dto.ResponseTaskDto;
import br.senai.sc.demo.controller.dto.TaskDto;
import br.senai.sc.demo.model.Task;
import br.senai.sc.demo.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskServiceInt {

    private final TaskRepository taskRepository;
    private final KafkaTemplate<String, TaskDto> kafkaTemplate;

    @Override
    public Task criarTask(TaskDto taskDto) {
        Task task = new Task(taskDto.nome(), null);
        System.out.println("Enviando mensagem ao tópico my_topic_karol:" + taskDto.nome());
        kafkaTemplate.send("topico_novo_karol", taskDto);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task verTask(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public List<Task> verTodas() {
        return taskRepository.findAll();
    }

    public Boolean excluirTask(Long id) {
        taskRepository.deleteById(id);
        return true;
    }

    public List<Task> verKafka() {
        return null;
    }
}
