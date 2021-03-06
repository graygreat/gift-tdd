package com.practice.gifttdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        Review review = new Review(1L, "재밌어요", "010-1111-2222");
        reviewRepository.save(review);
    }

    @Test
    void 후기_조회_성공() {
        // given

        // when
        Review review = reviewRepository.findById(1L)
                .orElseThrow(RuntimeException::new);

        // then
        assertThat(review.getId()).isEqualTo(1L);
        assertThat(review.getContent()).isEqualTo("재밌어요");
        assertThat(review.getPhoneNumber()).isEqualTo("010-1111-2222");
    }
}
