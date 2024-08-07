package br.senai.sc.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<File> files;

    public Task(String nome) {};

    public Task(String nome, List<File> files) {
        this.nome = nome;
        this.files = files;
    }

}
