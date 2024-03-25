package kea.keapoint.onlog.utils;

import feign.FeignException;
import kea.keapoint.onlog.exception.KarloApiException;
import kea.keapoint.onlog.feign.KarloServiceFeignClient;
import kea.keapoint.onlog.feign.dto.karlo.Image;
import kea.keapoint.onlog.feign.dto.karlo.ImageGenerationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Karlo 서비스를 사용하는 기능을 제공하는 서비스 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KarloService {

    private final KarloServiceFeignClient karloServiceFeignClient;

    /**
     * 주어진 영어 텍스트를 이미지로 변환하는 메소드
     *
     * @param englishText 이미지로 변환할 영어 텍스트
     * @return 생성된 이미지 파일 경로 리스트
     */
    public List<String> createImage(String englishText) {
        try {
            return Arrays.stream(
                            Objects.requireNonNull(
                                            karloServiceFeignClient.createImage(new ImageGenerationRequestDto(englishText))
                                                    .getBody()
                                    )
                                    .getImages()
                    )
                    .map(Image::getFile)
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
