package com.bahadirmemis.n11logger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bahadirmemis
 */
@Entity
@Getter
@Setter
public class ErrorLog {

  @Id
  @GeneratedValue(generator = "ErrorLog", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "ErrorLog", sequenceName = "ERROR_LOG_ID_SEQ")
  private Long id;

  private LocalDateTime date;
  private String message;
  private String description;
}
