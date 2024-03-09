package com.bahadirmemis.n11logger;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author bahadirmemis
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

  private final ErrorLogRepository errorLogRepository;

  @KafkaListener(topics = "logTopic", groupId = "log-consumer-group")
  public void consume(String message){

    log.info("consume started!");

    ErrorLog errorLog = new ErrorLog();
    errorLog.setDate(LocalDateTime.now());
    errorLog.setMessage(message);
    errorLog.setDescription("Kafka logs");

    errorLogRepository.save(errorLog);

    log.info("consume finished!");

  }
}
