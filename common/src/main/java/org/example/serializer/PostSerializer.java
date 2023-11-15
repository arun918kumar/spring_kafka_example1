package org.example.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.example.model.Post;

public class PostSerializer implements Serializer<Post> {

    private ObjectMapper objectMapper;

    @Override
    public byte[] serialize(String topic, Post data) {
        try {
            objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
