package com.example.service;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.model.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PostFirebaseConsumer {

    @Value("${firestore.collection}")
    private String FIRESTORE_COLLECTION;
    @Value("${firestore.key-path}")
    private String KEY_PATH;
    private final Logger LOGGER = LoggerFactory.getLogger(PostFirebaseConsumer.class);
    private ExecutorService executorService;

    @PostConstruct
    public void init() throws IOException {
        executorService = Executors.newFixedThreadPool(5);
        FileInputStream serviceAccount =
                new FileInputStream(KEY_PATH);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
    }

    @KafkaListener(topics = {"${spring.kafka.topic}"}, groupId = "${spring.kafka.groupId}")
    private void postListener(ConsumerRecord<Integer, Post> consumerRecord) throws ExecutionException, InterruptedException {
        savePost(consumerRecord.value());
    }
    
    public void savePost(Post post) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> result = firestore.collection(FIRESTORE_COLLECTION).document().set(post);
        result.addListener(() -> {
            try {
                LOGGER.info("Saved post with id {} at {}",post.getId(),result.get().getUpdateTime());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        },executorService);
    }

}
