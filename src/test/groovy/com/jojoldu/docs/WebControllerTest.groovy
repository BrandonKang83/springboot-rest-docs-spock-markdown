package com.jojoldu.docs

import io.restassured.builder.RequestSpecBuilder
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.Rule
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import spock.lang.Specification

import static io.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.is
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.templates.TemplateFormats.markdown

/**
 * Created by jojoldu@gmail.com on 2018. 4. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebControllerTest extends Specification {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation()

    private RequestSpecification spec

    @LocalServerPort
    private int port

    void setup() {
        this.spec = new RequestSpecBuilder()
                .addFilter(
                documentationConfiguration(restDocumentation)
                        .snippets()
                        .withTemplateFormat(markdown())) // Rest Docs 생성 템플릿 지정
                .build()
    }

    def "Response 필드 설명"() {
        expect:
        given(this.spec)
                .accept("application/json")
                .filter(document(
                "default-sample",
                preprocessRequest(
                        modifyUris()
                                .host('api.jojoldu.tistory.com')
                                .removePort(),
                        prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                        fieldWithPath('status').description('응답 상태 코드'),
                        fieldWithPath('message').description('응답 메세지'),
                )))
                .when()
                .port(this.port)
                .get("/")
                .then()
                .assertThat().statusCode(is(200))
                .assertThat().body("status", is("OK"))
                .assertThat().body("message", is("Hello World"))
    }

    def "Request & Response 설명" () {
        expect:
        def requestDto = RequestDto.builder()
                .age(32)
                .name("jojoldu")
                .email("jojoldu@gmail.com")
                .build()

        given(this.spec)
                .accept("application/json")
                .contentType(ContentType.JSON)
                .filter(document( // document 메소드 인자값을 기준으로 Markdown 생성
                "email-sample", // 현재 테스트로 생성되는 Markdown 파일들이 담길 디렉토리명
                preprocessRequest(
                        modifyUris() // 문서상 표기되는 URL과 Port 지정
                                .host('api.jojoldu.tistory.com')
                                .removePort()),
                preprocessResponse(prettyPrint()),
                requestFields( // request field 설명하는 Markdown 생성
                        fieldWithPath('name').description('이름'),
                        fieldWithPath('age').description('나이'),
                        fieldWithPath('email').description('Email'),
                        subsectionWithPath('tags').type(JsonFieldType.ARRAY).description('tag 목록')
                ),
                responseFields( // response field 설명하는 Markdown 생성
                        fieldWithPath('status').description('응답 상태 코드'),
                        fieldWithPath('message').description('응답 메세지'),
                )))
                .when()
                .port(this.port)
                .body(requestDto) // request body data
                .post("/email") // request url
                .then()
                .assertThat().statusCode(is(200))
                .assertThat().body("status", is("OK"))
                .assertThat().body("message", is("jojoldu@gmail.com"))
    }
}
