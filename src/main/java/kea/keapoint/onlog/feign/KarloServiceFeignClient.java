package kea.keapoint.onlog.feign;

import kea.keapoint.onlog.config.feign.KarloServiceFeignConfiguration;
import kea.keapoint.onlog.feign.dto.karlo.KarloImageGenerationRequestDto;
import kea.keapoint.onlog.feign.dto.karlo.KarloImageGenerationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "karlo-service", url = "https://api.kakaobrain.com/v2/inference/karlo", configuration = KarloServiceFeignConfiguration.class)
public interface KarloServiceFeignClient {

    /**
     * Karlo Image Generation API 요청
     *
     * @param dto Karlo Image Generation API 요청 DTO
     * @return Karlo Image Generation API 응답 DTO
     */
    @PostMapping(value = "/t2i")
    ResponseEntity<KarloImageGenerationResponseDto> createImage(KarloImageGenerationRequestDto dto);
}
