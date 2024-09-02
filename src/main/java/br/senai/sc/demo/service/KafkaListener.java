package br.senai.sc.demo.service;

import br.senai.sc.demo.controller.dto.TaskDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaListener {

    @org.springframework.kafka.annotation.KafkaListener(topics = "topico_novo_karol", containerFactory = "kafkaListenerContainerFactory")
    public void listen(TaskDto taskDto) {
        System.out.println("Mensagem recebida: " + taskDto.nome());
    }

}
