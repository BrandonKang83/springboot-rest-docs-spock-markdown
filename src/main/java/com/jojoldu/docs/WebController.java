package com.jojoldu.docs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jojoldu@gmail.com on 2018. 4. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RestController
public class WebController {

    @GetMapping(value = "/")
    public ResponseDto index() {
        return new ResponseDto(HttpStatus.OK, "Hello World");
    }

    @PostMapping(value = "/email")
    public ResponseDto process(@RequestBody final RequestDto requestDto) {
        return new ResponseDto(HttpStatus.OK, requestDto.getEmail());
    }
}
