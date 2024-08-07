package br.senai.sc.demo.service;

import br.senai.sc.demo.controller.dto.FileDto;
import br.senai.sc.demo.controller.dto.ResponseTaskDto;
import br.senai.sc.demo.controller.dto.TaskDto;
import br.senai.sc.demo.model.File;
import br.senai.sc.demo.model.Task;

import java.util.List;

public interface TaskServiceInt {
    Task criarTask(TaskDto taskDto);
    Task verTask(Long id);
    List<Task> verTodas();
}
