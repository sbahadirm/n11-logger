package com.bahadirmemis.n11logger.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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

  private static void doIt(Message message) {

    log.info(message.toString());
    //if ("bahadir".equals("bahadir")) {
    throw new RuntimeException();
    //}
  }

  @RabbitListener(queues = "queueName")
  public void consume(Message message) {

    MessageProperties props = message.getMessageProperties();
    Integer retries = props.getHeader("x-retries");
    if (retries == null) {
      retries = 0;
    }

    try {
      doIt(message);
    } catch (Exception e) {

      if (retries < 3) {
        retries++;
        props.setHeader("x-retries", retries);
        rabbitTemplate.send(props.getReceivedExchange(), props.getReceivedRoutingKey(),
                            message);
      } else {
        rabbitTemplate.convertAndSend("dlx.exchangeName", "dlq.routingKey", message);
      }
    }
  }

  @RabbitListener(queues = "dlq.queueName")
  public void consumeDLQ(String message) {
    log.error("Received message from DLT Queue " + message);
  }
}
