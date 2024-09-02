package br.senai.sc.demo.controller.dto;

public record KafkaRecord(String topic, String key, String value) {
}
