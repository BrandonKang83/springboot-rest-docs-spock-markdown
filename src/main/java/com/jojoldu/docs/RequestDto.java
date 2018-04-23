package com.jojoldu.docs;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 4. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
public class RequestDto {

    private String name;
    private int age;
    private String email;
    private List<String> tags;

    @Builder
    public RequestDto(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.tags = new ArrayList<>();
    }
}
