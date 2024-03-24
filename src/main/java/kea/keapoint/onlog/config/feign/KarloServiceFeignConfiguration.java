package kea.keapoint.onlog.config.feign;

import feign.RequestInterceptor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class KarloServiceFeignConfiguration {

    @Value("${karlo.client-id}")
    private String clientId;

    @Bean
    public RequestInterceptor karloServiceFeignRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "KakaoAK " + clientId);
            requestTemplate.header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        };
    }

}
