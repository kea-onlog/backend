package kea.keapoint.onlog.utils;

import feign.FeignException;
import kea.keapoint.onlog.exception.KarloApiException;
import kea.keapoint.onlog.feign.KarloServiceFeignClient;
import kea.keapoint.onlog.feign.dto.karlo.KarloImage;
import kea.keapoint.onlog.feign.dto.karlo.KarloImageGenerationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KarloService {

    private final KarloServiceFeignClient karloServiceFeignClient;

    public List<String> createImage(String englishText) {
        try {
            return Arrays.stream(
                            karloServiceFeignClient.createImage(new KarloImageGenerationRequestDto(englishText))
                                    .getBody()
                                    .getImages()
                    )
                    .map(KarloImage::getImage)
                    .toList();

        } catch (FeignException e) {
            log.warn("이미지를 생성하는데 실패했습니다. HTTP 상태 코드: {}, 응답 본문: {}", e.status(), e.contentUTF8());
            throw new KarloApiException(e.status(), e.contentUTF8());

        } catch (Exception e) {
            log.error("Karlo 이미지 생성 API 호출에 실패했습니다. {}", e.getMessage());
            throw e;
        }
    }
}
