package com.bahadirmemis.n11logger;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bahadirmemis
 */
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {

}
