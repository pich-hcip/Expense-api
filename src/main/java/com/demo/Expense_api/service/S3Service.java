package com.demo.Expense_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final String bucketName;
    private final String region;
    private final S3Client s3Client;

    public S3Service(
            @Value("${aws.access.key}") String accessKey,
            @Value("${aws.secret.key}") String secretKey,
            @Value("${aws.session.token}") String sessionToken,
            @Value("${aws.region}") String region,
            @Value("${aws.s3.bucket-name}") String bucketName) {

        this.region = region;
        this.bucketName = bucketName;

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsSessionCredentials.create(accessKey, secretKey, sessionToken)
                ))
                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("profile-images/" + fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return String.format("https://%s.s3.%s.amazonaws.com/profile-images/%s", bucketName, region, fileName);
    }
}