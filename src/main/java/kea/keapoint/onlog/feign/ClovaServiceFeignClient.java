package kea.keapoint.onlog.feign;

import kea.keapoint.onlog.config.feign.ClovaServiceFeignConfiguration;
import kea.keapoint.onlog.feign.dto.ClovaSummaryRequestDto;
import kea.keapoint.onlog.feign.dto.ClovaSummaryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Clova Summary API 요청을 위한 Feign Client
 *
 * @see <a href="https://api.ncloud-docs.com/docs/ai-naver-clovasummary-api">Clova Summeary API 명세서</a>
 */
@FeignClient(name = "clova-service", url = "https://naveropenapi.apigw.ntruss.com", configuration = ClovaServiceFeignConfiguration.class)
public interface ClovaServiceFeignClient {

    /**
     * Clova Summary API 요청
     *
     * @param dto          Clova Summary API 요청 DTO
     * @return Clova Summary API 응답 DTO
     */
    @PostMapping("/text-summary/v1/summarize")
    ResponseEntity<ClovaSummaryResponseDto> summarize(ClovaSummaryRequestDto dto);

}
