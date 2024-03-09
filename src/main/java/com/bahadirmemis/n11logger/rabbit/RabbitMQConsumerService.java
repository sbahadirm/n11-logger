package com.bahadirmemis.n11logger.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author bahadirmemis
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQConsumerService {

  private final RabbitTemplate rabbitTemplate;

  private static void doIt(String message) {

    if ("bahadir".equals(message)) {
      throw new RuntimeException();
    }
    log.info(message);
  }

  @RabbitListener(queues = "queueName")
  public void consume(String message) {
    try {
      doIt(message);
    } catch (Exception e){
      rabbitTemplate.convertAndSend("dlx.exchangeName", "dlq.routingKey", message);
    }
  }

  @RabbitListener(queues = "dlq.queueName")
  public void consumeDLQ(String message) {
    log.error("Received message from DLT Queue " + message);
  }
}
