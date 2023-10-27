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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImageAndGetUrl(HttpServletRequest request) throws Exception {
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

    public void deleteImage(String url) {
        try {
            /* Extract the folder name using regular expression
             * Example url : http://res.cloudinary.com/dk9fdcnnp/image/upload/v1629781233/books/1.png
             * */
            String pattern = "/v\\d+/(.*?)/[^/]+\\.\\w+";
            Pattern regexPattern = Pattern.compile(pattern);
            Matcher matcher = regexPattern.matcher(url);

            if (matcher.find()) {
                String folderName = matcher.group(1);

                // Extract the public ID
                String publicId = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));

                // Construct full public ID
                String fullPublicId = folderName + "/" + publicId;

                // Delete the image using full public ID
                cloudinary.uploader().destroy(fullPublicId, ObjectUtils.emptyMap());
            } else {
                throw new RuntimeException("Folder name not found in URL");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
