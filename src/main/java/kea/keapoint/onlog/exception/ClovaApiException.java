package kea.keapoint.onlog.exception;

import lombok.Getter;

@Getter
public class ClovaApiException extends RuntimeException {

    private final int statusCode;
    private final Object responseBody;

    public ClovaApiException(int statusCode, Object responseBody) {
        super("텍스트를 요약하는데 실패했습니다. HTTP 상태 코드: " + statusCode + ", 응답 본문: " + responseBody);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

}