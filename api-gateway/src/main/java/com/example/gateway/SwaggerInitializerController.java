package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SwaggerInitializerController {

    private static final String JS = """
            window.onload = function() {
              window.ui = SwaggerUIBundle({
                configUrl: "/v3/api-docs/swagger-config",
                dom_id: '#swagger-ui',
                deepLinking: true,
                presets: [
                  SwaggerUIBundle.presets.apis,
                  SwaggerUIStandalonePreset
                ],
                plugins: [
                  SwaggerUIBundle.plugins.DownloadUrl
                ],
                layout: "StandaloneLayout"
              });
            };
            """;

    @Bean
    public RouterFunction<ServerResponse> swaggerInitializerRoute() {
        return RouterFunctions.route()
                .GET("/swagger-ui/swagger-initializer.js", req ->
                        ServerResponse.ok()
                                .contentType(MediaType.parseMediaType("application/javascript"))
                                .bodyValue(JS))
                .build();
    }
}
