package com.lnu.foundation.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by rucsac on 12/10/2018.
 */

@Configuration
//@EnableWebSecurity
public class WebSecurityConfiguration /**extends WebSecurityConfigurerAdapter**/ {

/**
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                //.anyRequest().authenticated()
                .anyRequest().permitAll()
                .and().
            //    cors()
              //  .and().
                httpBasic();
    }

    /**
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }**/

}

