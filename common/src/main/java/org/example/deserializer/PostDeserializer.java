package org.example.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.model.Post;

import java.io.IOException;

public class PostDeserializer implements Deserializer<Post> {

    private ObjectMapper objectMapper;

    @Override
    public Post deserialize(String topic, byte[] data) {
        objectMapper = new ObjectMapper();
        if(null != data) {
            try {
                return objectMapper.readValue(data, Post.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
