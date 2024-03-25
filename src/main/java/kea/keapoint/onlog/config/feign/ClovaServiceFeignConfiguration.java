package kea.keapoint.onlog.config.feign;

import feign.RequestInterceptor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Clova 서비스 Feign Client 설정 클래스
 */
public class ClovaServiceFeignConfiguration {

    @Value("${clova.client-id}")
    private String clientId;

    @Value("${clova.client-secret}")
    private String clientSecret;

    @Bean
    public RequestInterceptor clovaServiceFeignRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-NCP-APIGW-API-KEY-ID", clientId);
            requestTemplate.header("X-NCP-APIGW-API-KEY", clientSecret);
            requestTemplate.header("Content-Type", ContentType.APPLICATION_JSON.toString());
        };
    }
}
