package kea.keapoint.onlog.utils;

import feign.FeignException;
import kea.keapoint.onlog.exception.ClovaApiException;
import kea.keapoint.onlog.feign.ClovaServiceFeignClient;
import kea.keapoint.onlog.feign.dto.clova.SummaryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Clova 서비스를 사용하는 기능을 제공하는 서비스 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClovaService {

    private final ClovaServiceFeignClient clovaServiceFeignClient;

    /**
     * 주어진 텍스트를 요약하는 메소드
     *
     * @param text 요약할 텍스트
     * @return 요약된 텍스트
     */
    public String summarize(String text) {
        try {
            return clovaServiceFeignClient.summarize(new SummaryRequestDto(text))
                    .getBody()
                    .getSummary();

        } catch (FeignException e) {
            log.warn("텍스트를 요약하는데 실패했습니다. HTTP 상태 코드: {}, 응답 본문: {}", e.status(), e.contentUTF8());
            throw new ClovaApiException(e.status(), e.contentUTF8());

        } catch (Exception e) {
            log.error("Clova Summary API 호출에 실패했습니다. {}", e.getMessage());
            throw e;
        }
    }
}
