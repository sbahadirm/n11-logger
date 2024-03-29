package com.bahadirmemis.n11logger.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bahadirmemis
 */
@Configuration
public class RabbitMQConfig {

  @Bean
  public Queue queue() {
    return new Queue("queueName", false);
  }

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange("exchangeName");
  }

  @Bean
  public Binding binding() {
    return BindingBuilder.bind(queue()).to(exchange()).with("routingKey");
  }

  @Bean
  public Queue dlq() {
    return new Queue("dlq.queueName", false);
  }

  @Bean
  public DirectExchange dlx() {
    return new DirectExchange("dlx.exchangeName");
  }

  @Bean
  public Binding bindingDlq() {
    return BindingBuilder.bind(dlq()).to(dlx()).with("dlq.routingKey");
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost("localhost");
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    return connectionFactory;
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(jsonMessageConverter());
    return factory;
  }

  //@Bean
  //Queue queue() {
  //  return QueueBuilder.durable("queueName").withArgument("x-dead-letter-exchange", "dlx.exchangeName")
  //                     .withArgument("x-dead-letter-routing-key", "dlq.routingKey").build();
  //}
}
