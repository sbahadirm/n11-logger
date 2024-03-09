package com.bahadirmemis.n11logger.log;

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

  @KafkaListener(topics = "errorLog", groupId = "log-consumer-group")
  public void consume(String message){

    log.info("consume started!");

    ErrorLog errorLog = new ErrorLog();
    errorLog.setDate(LocalDateTime.now());
    errorLog.setMessage(message);
    errorLog.setDescription("Error");

    errorLogRepository.save(errorLog);

    log.info("consume finished!");

  }

  @KafkaListener(topics = "infoLog", groupId = "log-consumer-group")
  public void consumeInfos(String message){

    log.info("consume started!");

    ErrorLog errorLog = new ErrorLog();
    errorLog.setDate(LocalDateTime.now());
    errorLog.setMessage(message);
    errorLog.setDescription("Info");

    errorLogRepository.save(errorLog);

    log.info("consume finished!");

  }
}
