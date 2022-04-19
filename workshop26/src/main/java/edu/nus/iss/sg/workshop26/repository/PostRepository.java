package edu.nus.iss.sg.workshop26.repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import edu.nus.iss.sg.workshop26.models.Post;

@Repository
public class PostRepository {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private JdbcTemplate template;

    public Integer uploadPost(Post post) {
        return template.update(Queries.SQL_INSERT_POST, 
                                post.getImage(), 
                                post.getComment(),
                                post.getPoster(), 
                                post.getMediaType());
    }

    public Optional<Post> getPostById(Integer id) {
        return template.query(Queries.SQL_SELECT_POST_BY_ID, 
            (ResultSet rs) -> {
                if(!rs.next())
                    return Optional.empty();
                return Optional.of(Post.create(rs));
            },
            id);
    }

    public String uploadPostS3(MultipartFile file) throws IOException {
        String objId = UUID.randomUUID().toString().substring(0, 8);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        PutObjectRequest putReq = new PutObjectRequest("dumpbucket", objId, file.getInputStream(), metadata);
        putReq.setCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(putReq);

        return objId;
    }

    public Optional<S3Object> getPostS3(String id) {
        GetObjectRequest getReq = new GetObjectRequest("dumpbucket", id);
        S3Object obj = s3.getObject(getReq);

        if(obj == null)
            return Optional.empty();
        
        return Optional.of(obj);
    }
}
