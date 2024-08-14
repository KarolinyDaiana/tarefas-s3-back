package br.senai.sc.demo.service;

import br.senai.sc.demo.controller.dto.FileDto;
import br.senai.sc.demo.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileServiceInt {
    Boolean criarFile(Long id, MultipartFile multipartFile);
    String verFile(Long idFile);
    String excluirFile(Long idFile);
}
