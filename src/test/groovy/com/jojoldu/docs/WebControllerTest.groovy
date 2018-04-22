package com.jojoldu.docs

import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.junit.Rule
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.restdocs.JUnitRestDocumentation
import spock.lang.Specification

import static io.restassured.RestAssured.given
import static org.hamcrest.CoreMatchers.is
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
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
                        .withTemplateFormat(markdown()))
                .build()
    }

    def "기본 요청"() {
        expect:
        given(this.spec)
                .accept("application/json")
                .filter(document(
                "default-sample",
                preprocessRequest(
                        modifyUris()
                                .host('api.jojoldu.tistory.com')
                                .removePort()),
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
}
