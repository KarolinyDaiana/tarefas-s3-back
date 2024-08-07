package br.senai.sc.demo.service;

import br.senai.sc.demo.config.S3Config;
import br.senai.sc.demo.controller.dto.FileDto;
import br.senai.sc.demo.model.File;
import br.senai.sc.demo.model.Task;
import br.senai.sc.demo.repository.FileRepository;
import br.senai.sc.demo.repository.TaskRepository;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Service
public class FileServiceImpl implements FileServiceInt {

    private final S3Config config;
    FileRepository fileRepository;
    TaskRepository taskRepository;

    @Override
    public Boolean criarFile(Long idTask, MultipartFile multipartFile) {
        try {
            /*
            * Criação das variáveis que contém as chaves de acesso e o nome do bucket a ser utiliado.
            * */
            String awsKeyId = config.getAccessKey();
            String awsSecretKey = config.getSecretKey();
            String bucketName = config.getBucketName();

            /*
            * Criação do UUID para a imagem, seguido da data e horário que foi criada.
            * */
            String chave = UUID.randomUUID().toString();
            LocalDateTime data = LocalDateTime.now();

            /*
            * Variável task a partir do id passado pelo pathVariable.
            * */
            Task task = taskRepository.findById(idTask).get();
            /*
            * Nova variável file, já com o UUID(chave), a data e a task.
            * Então, o salvamento desta file na repository(banco) e na task correspondente.
            * */
            File file = new File(null, chave, data, task);
            fileRepository.save(file);
            task.getFiles().add(file);

            /*
            * Pegando as credenciais para acesso na s3.
            * */
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKeyId, awsSecretKey);

            /*
             * Cliente criado com as credenciais.
             * */
            AmazonS3 client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1).build();

            /*
             * Verificação da existência do bucket.
             * */
            if(!client.doesBucketExistV2(bucketName)) {
                return false;
            }

//            InputStream inputStream = new StringInputStream(file.getRef());

//            TransferManagerUtils fileTransfer = new TransferManagerUtils();

//            UploadObjectRequest uploadObjectRequest = UploadObjectRequest.build()
//                    .inputStream(multipartFile.getInputStream())
//                    .bucket(bucketName)
//                    .key(awsKeyId)
//                    .contentType(multipartFile.getContentType())
//                    .build();
            /*
             *
             * */
            java.io.File novaFile = java.io.File.createTempFile(chave, multipartFile.getOriginalFilename());
            multipartFile.transferTo(novaFile);
            client.putObject(bucketName, chave, novaFile);
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType(multipartFile.getContentType());
//            objectMetadata.setContentLength(multipartFile.getSize());
//            client.putObject(bucketName, chaveAws, multipartFile.getInputStream(), objectMetadata);
            return true;

            /*
             * Catches para as possíveis excessões.
             * */
        } catch (AmazonS3Exception e) {
            System.out.println("Erro aws: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro comum: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String verFile(Long id) {
        try {
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());

            AmazonS3 client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1).build();

            String fileRef = fileRepository.findById(id.intValue()).get().getRef();

            System.out.println(fileRef);
            if(fileRef == null) {
                return "Erro: a referênia é inexistente!";
            }

            GeneratePresignedUrlRequest presigned =
                    new GeneratePresignedUrlRequest(config.getBucketName(), fileRef);
            presigned.setExpiration(new Date(System.currentTimeMillis() + 600000));

            String url = client.generatePresignedUrl(presigned).toString();
            System.out.println(url);

            return url;

        } catch (AmazonS3Exception e) {
            System.out.println("Erro aws: " + e.getMessage());
            return "Erro aws: " + e.getMessage();
        } catch (Exception e) {
            System.out.println("Erro comum: " + e.getMessage());
            return "Erro comum: " + e.getMessage();
        }
    }
}
