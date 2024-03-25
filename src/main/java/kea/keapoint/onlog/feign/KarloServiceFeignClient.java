package kea.keapoint.onlog.feign;

import kea.keapoint.onlog.config.feign.KarloServiceFeignConfiguration;
import kea.keapoint.onlog.feign.dto.karlo.ImageGenerationRequestDto;
import kea.keapoint.onlog.feign.dto.karlo.ImageGenerationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Karlo 서비스 Feign Client
 *
 * @see <a href="https://developers.kakao.com/docs/latest/ko/karlo/rest-api">Karlo API 명세서</a>
 */
@FeignClient(name = "karlo-service", url = "https://api.kakaobrain.com/v2/inference/karlo", configuration = KarloServiceFeignConfiguration.class)
public interface KarloServiceFeignClient {

    /**
     * Karlo Image Generation API 요청
     *
     * @param dto Karlo Image Generation API 요청 DTO
     * @return Karlo Image Generation API 응답 DTO
     */
    @PostMapping(value = "/t2i")
    ResponseEntity<ImageGenerationResponseDto> createImage(ImageGenerationRequestDto dto);
}
