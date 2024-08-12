package br.senai.sc.demo.controller;

import br.senai.sc.demo.controller.dto.FileDto;
import br.senai.sc.demo.model.File;
import br.senai.sc.demo.service.FileServiceImpl;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
@CrossOrigin("*")
public class FileController {

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }
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
