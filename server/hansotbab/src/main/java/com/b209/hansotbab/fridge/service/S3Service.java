package com.b209.hansotbab.fridge.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.b209.hansotbab.fridge.dto.request.ProductCreateRequestDTO;
import com.b209.hansotbab.fridge.dto.request.ProductUpdateRequestDTO;
import com.b209.hansotbab.fridge.dto.response.ProductDetailResponseDTO;
import com.b209.hansotbab.fridge.dto.response.ProductListResponseDTO;
import com.b209.hansotbab.fridge.entity.*;
import com.b209.hansotbab.fridge.repository.FridgeLikeRepository;
import com.b209.hansotbab.fridge.repository.FridgeRepository;
import com.b209.hansotbab.fridge.repository.ProductBringRepository;
import com.b209.hansotbab.fridge.repository.ProductRepository;
import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@Transactional
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FridgeRepository fridgeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductBringRepository productBringRepository;

    @Autowired
    private FridgeLikeRepository fridgeLikeRepository;

    @Autowired
    private AmazonS3Client amazonS3Client;


    public void deleteFile(String originalName) {
        amazonS3Client.deleteObject(bucket, originalName);
    }

    public String[] uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String originName = file.getOriginalFilename();
        String changedName = changedImageName(originName);
        amazonS3Client.putObject(new PutObjectRequest(bucket, changedName, fileObj));
        fileObj.delete();

        String newUrl = amazonS3Client.getUrl(bucket, changedName).toString();
        return new String[]{changedName, newUrl};
    }

    public String[] uploadNewFile(MultipartFile file, Product product) {
        File fileObj = convertMultiPartFileToFile(file);
        String originName = file.getOriginalFilename();
        String changedName = changedImageName(originName);
        amazonS3Client.putObject(new PutObjectRequest(bucket, changedName, fileObj));
        fileObj.delete();

        String oldName = product.getProductImageName();
        deleteFile(oldName); //기존 파일 존재시 삭제

        String newUrl = amazonS3Client.getUrl(bucket, changedName).toString();
        return new String[]{changedName, newUrl};
    }

    private String changedImageName(String originName) { //이미지 이름 중복 방지를 위해 랜덤으로 생성
        String random = UUID.randomUUID().toString();
        return random + originName;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
//            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
