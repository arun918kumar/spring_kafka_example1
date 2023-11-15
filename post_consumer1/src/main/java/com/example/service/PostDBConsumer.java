package com.example.service;

import com.example.entity.Post;
import com.example.repo.PostRepo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PostDBConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(PostDBConsumer.class);
    @Autowired
    private PostRepo postRepo;

    @KafkaListener(topics = {"${spring.kafka.topic}"}, groupId = "${spring.kafka.groupId}")
    private void postListener(ConsumerRecord<Integer, org.example.model.Post> consumerRecord) {
        Post post = new Post();
        BeanUtils.copyProperties(consumerRecord.value(),post);
        postRepo.save(post);
        LOGGER.info("Saved post with id {}",post.getId());
    }

}
