package com.jojoldu.docs;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by jojoldu@gmail.com on 2018. 4. 22.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class ResponseDto {
    private HttpStatus status;
    private String message;

    public ResponseDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
