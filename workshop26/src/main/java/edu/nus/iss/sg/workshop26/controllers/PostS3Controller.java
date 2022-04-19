package edu.nus.iss.sg.workshop26.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class PostS3Controller {

    @Autowired
    private AmazonS3 s3;
    
    @PostMapping(path="/post/s3")
    public ResponseEntity<String> postUpload(@RequestParam MultipartFile file, 
                                                @RequestPart String poster,
                                                @RequestPart String comment) {
        JsonObject result;
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentType(file.getContentType());
        metaData.setContentLength(file.getSize());
        Map<String, String> userMetaData = new HashMap<>();
        userMetaData.put("filename", file.getOriginalFilename());
        userMetaData.put("timestamp", new Date().toString());
        userMetaData.put("uploader", poster);
        userMetaData.put("comment", comment);
        metaData.setUserMetadata(userMetaData);

        try {
            PutObjectRequest putReq = new PutObjectRequest("dumpbucket", 
                                        "%s/images/%s".formatted(poster, uuid),
                                        file.getInputStream(), 
                                        metaData);

            putReq.setCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(putReq);
            result = Json.createObjectBuilder()
                                .add("objId", uuid)
                                .build();
            
            return ResponseEntity.ok().body(result.toString());
        }catch(IOException ex){
            ex.printStackTrace();
            result = Json.createObjectBuilder()
            .add("error", ex.getMessage())
            .build();
            return ResponseEntity.internalServerError().body(result.toString());
        }
    }

    @GetMapping(path="/post/s3/{poster}/images/{uuid}")
    public ResponseEntity<byte[]> getImage(@PathVariable String poster, @PathVariable String uuid) {
        
        GetObjectRequest getReq = new GetObjectRequest("dumpbucket", "%s/images/%s".formatted(poster, uuid));
        S3Object obj = s3.getObject(getReq);
        if(obj == null)
            return ResponseEntity.notFound().build();
        
        ObjectMetadata metadata = obj.getObjectMetadata();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", metadata.getContentType());
        headers.setContentLength(metadata.getContentLength());
        headers.set("X-Original-Name", metadata.getUserMetaDataOf("filename"));
        headers.set("X-Create-Time", metadata.getUserMetaDataOf("timestamp"));
        headers.set("X-Uploader", metadata.getUserMetaDataOf("uploader"));
        headers.set("X-Notes", metadata.getUserMetaDataOf("comment"));

        byte[] buff = new byte[0];
        try{
            buff = IOUtils.toByteArray(obj.getObjectContent());
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok().headers(headers).body(buff);
    }
}
