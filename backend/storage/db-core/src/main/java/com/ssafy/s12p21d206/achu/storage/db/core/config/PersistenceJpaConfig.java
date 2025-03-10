package com.ssafy.s12p21d206.achu.storage.db.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "org.dukcode.todo.storage.db.core")
@EnableJpaRepositories(basePackages = "org.dukcode.todo.storage.db.core")
@Configuration
public class PersistenceJpaConfig {}
