package br.senai.sc.demo.controller;

import br.senai.sc.demo.service.FileServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
@CrossOrigin("*")
public class FileController {

    private final FileServiceImpl fileService;

    @PostMapping("/{idTask}")
    public ResponseEntity<Boolean> criarFile(@PathVariable Long idTask, @RequestBody MultipartFile file) {
        return new ResponseEntity<>(fileService.criarFile(idTask, file), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{idFile}")
    public ResponseEntity<String> verFile(@PathVariable Long idFile) {
        return new ResponseEntity<>(fileService.verFile(idFile), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{idFile}")
    public ResponseEntity<String> deletarFile(@PathVariable Long idFile) {
        return new ResponseEntity<>(fileService.excluirFile(idFile), HttpStatusCode.valueOf(200));
    }
}
