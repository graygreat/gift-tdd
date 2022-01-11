package com.practice.gifttdd;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        Review review = new Review(1L, "재밌어요", "010-1111-2222");
        reviewRepository.save(review);
    }

    @Test
    void 후기_조회_성공() {
        // given
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
        // when
        .when()
                .get("/reviews/1")
        // then
        .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("id", equalTo(1))
                .body("content", equalTo("재밌어요"))
                .body("phoneNumber", equalTo("010-1111-2222"));
    }

    @Test
    void 후기_조회_실패() {
        // given
        given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
        // when
        .when()
                .get("/reviews/1000")
        // then
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void 선물하기_성공() {

    }

    @Test
    void 선물하기_실패() {

    }
}
