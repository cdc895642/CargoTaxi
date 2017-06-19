package com.cargotaxi.config;

import org.springframework.security.web.context
        .AbstractSecurityWebApplicationInitializer;

/**
 * Automatically register the springSecurityFilterChain Filter for every URL in app
 * Add a ContextLoaderListener that loads the SecurityConfig.
 */
public class SpringSecurityInit extends AbstractSecurityWebApplicationInitializer{
}
