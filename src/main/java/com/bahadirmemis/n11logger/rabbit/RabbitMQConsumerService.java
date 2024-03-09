package com.bahadirmemis.n11logger.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author bahadirmemis
 */
@Service
@Slf4j
public class RabbitMQConsumerService {

  @RabbitListener(queues = "queueName")
  public void consume(String message) {

    log.info(message);

  }
}
