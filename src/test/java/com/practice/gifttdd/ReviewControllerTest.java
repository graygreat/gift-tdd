package com.practice.gifttdd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private Long id = 1L;
    private String content = "재밌어요";
    private String phoneNumber = "010-1111-2222";

    @Test
    void 후기_조회_성공() throws Exception {
        // given
        given(reviewService.getById(id))
                .willReturn(new Review(id, content, phoneNumber));

        // when
        ResultActions perform = mockMvc.perform(get("/reviews/" + id));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("content").value(content))
                .andExpect(jsonPath("phoneNumber").value(phoneNumber));
    }

    @Test
    void 후기_조회_실패() throws Exception {
        // given
        given(reviewService.getById(1000L))
                .willThrow(new ReviewNotFoundException());

        // when
        ResultActions perform = mockMvc.perform(get("/reviews/" + 1000));

        // then
        perform
                .andExpect(status().isNotFound());
    }
}
