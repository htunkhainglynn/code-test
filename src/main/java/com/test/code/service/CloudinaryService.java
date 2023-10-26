package com.test.code.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.test.code.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImageAndGetUrl(BookDto bookDto, HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("image");  // get image from request

        String folderName = createFolder();
        return uploadFile(folderName, multipartFile);
    }

    public String uploadFile(String folderName, MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);

            Map<String, String> uploadOptions = new HashMap<>();
            uploadOptions.put("folder", folderName);

            String url = cloudinary.uploader().upload(file, uploadOptions).get("url").toString();
            boolean isDeleted = file.delete();  // delete image after upload

            if (isDeleted) {
                log.info("File successfully deleted");
            } else {
                log.warn("File doesn't exist");
            }

            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createFolder() throws Exception {
        cloudinary.api().createFolder("books", ObjectUtils.emptyMap());
        return "books";
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
