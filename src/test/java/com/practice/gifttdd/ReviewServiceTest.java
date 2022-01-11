package com.practice.gifttdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ReviewServiceTest {

    ReviewRepository reviewRepository = mock(ReviewRepository.class);
    ReviewService reviewService = new ReviewService(reviewRepository);

    private Long id = 1L;
    private String content = "재밌어요";
    private String phoneNumber = "010-1111-2222";

    @Test
    void 후기_조회_성공() throws Exception {
        // given
        given(reviewRepository.findById(id))
                .willReturn(Optional.of(new Review(id, content, phoneNumber)));

        // when
        Review review = reviewService.getById(id);

        // then
        Assertions.assertThat(review.getId()).isEqualTo(id);
        Assertions.assertThat(review.getContent()).isEqualTo(content);
        Assertions.assertThat(review.getPhoneNumber()).isEqualTo(phoneNumber);
    }

    @Test
    void 후기_조회_실패() throws Exception {
        // given
        given(reviewRepository.findById(id))
                .willReturn(Optional.empty());

        assertThatThrownBy(() ->
                reviewService.getById(1000L))
                .isInstanceOf(ReviewNotFoundException.class);
    }
}
