package com.example.service;

import org.example.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

@Service
public class PostProducer implements ApplicationRunner {

    private final Logger LOGGER = LoggerFactory.getLogger(PostProducer.class);
    @Value("${spring.kafka.topic}")
    private String kafkaTopic;
    @Autowired
    private KafkaTemplate<Integer, Post> kafkaTemplate;
    @Autowired
    private RestTemplate restTemplate;
    private static final String POST_DATA_API = "https://jsonplaceholder.typicode.com/posts/";


    @Override
    public void run(ApplicationArguments args) throws Exception {
        producePostData();
    }

    private void producePostData() {
        IntStream.range(1,20)
                .mapToObj(postId -> fetchPost(postId))
                .forEach(post -> {
                    kafkaTemplate.send(kafkaTopic,post.getId(),post);
                });
        LOGGER.info("Successfully produced 50 post to the topic {}",kafkaTopic);
    }

    private Post fetchPost(int id) {
       return restTemplate.getForObject(POST_DATA_API+id,Post.class);
    }

}
