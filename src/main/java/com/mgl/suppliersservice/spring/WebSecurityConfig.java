package com.mgl.suppliersservice.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Required by HeroKu.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * .
     *
     * @param http .
     *
     * @throws Exception .
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requiresChannel()
            .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
            .requiresSecure();

        http.cors()
            .and()
            .csrf()
            .disable();
    }

}
