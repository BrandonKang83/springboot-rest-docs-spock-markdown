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
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration

/**
 * Created by jojoldu@gmail.com on 2018. 4. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebControllerTest extends Specification{

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation()

    private RequestSpecification spec

    @LocalServerPort
    private int port

    void setup() {
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build()
    }

    def "기본 출력"() {
        expect:
        given(this.spec)
                .accept("application/json")
//                .filter(document("sample", preprocessRequest(modifyUris()
//                        .scheme("https")
//                        .host("api.example.com")
//                        .removePort())))
                .when()
                .port(this.port)
                .get("/")
                .then()
                .assertThat().statusCode(is(200))
                .assertThat().body(is("Hello World"))
    }
}
