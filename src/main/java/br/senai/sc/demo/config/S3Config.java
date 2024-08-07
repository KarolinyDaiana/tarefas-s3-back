package br.senai.sc.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@PropertySource("classpath:aws.properties")
public class S3Config {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.access.key}")
    private String secretKey;

    @Value("${aws.bucket.name}")
    private String bucketName;

}
