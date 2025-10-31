package com.onnjoy.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    /**
     * Generate a pre-signed URL for uploading a video to S3
     * @param s3Key The S3 key (path) where the file will be stored
     * @return Pre-signed URL valid for 10 minutes
     */
    public String generatePresignedUploadUrl(String s3Key) {
        try {
            // Create a PutObjectRequest
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType("video/mp4") // or detect from file extension
                    .build();

            // Create a PresignRequest with 10 minutes expiration
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(putObjectRequest)
                    .build();

            // Generate the pre-signed URL
            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

            return presignedRequest.url().toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate pre-signed URL: " + e.getMessage(), e);
        }
    }

    /**
     * Get the public URL for an uploaded video
     * @param s3Key The S3 key of the uploaded file
     * @return Public S3 URL
     */
    public String getPublicUrl(String s3Key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                s3Client.serviceClientConfiguration().region().id(),
                s3Key);
    }

    /**
     * Check if a file exists in S3
     * @param s3Key The S3 key to check
     * @return true if file exists, false otherwise
     */
    public boolean doesFileExist(String s3Key) {
        try {
            s3Client.headObject(builder -> builder.bucket(bucketName).key(s3Key));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}