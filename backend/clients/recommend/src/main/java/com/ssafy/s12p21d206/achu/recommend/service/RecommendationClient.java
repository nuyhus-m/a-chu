package com.ssafy.s12p21d206.achu.recommend.service;

import com.ssafy.s12p21d206.achu.recommend.dto.RecommendationResponse;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecommendationClient {

  private static final Logger logger = Logger.getLogger(RecommendationClient.class.getName());
  private final RestTemplate restTemplate;
  private final String recommendationBaseUrl;

  public RecommendationClient(@Value("${recommendation.base-url}") String recommendationBaseUrl) {
    this.restTemplate = new RestTemplate();
    this.recommendationBaseUrl = recommendationBaseUrl;
  }

  public List<Long> getRecommendedGoods(Long babyId) {
    String url = recommendationBaseUrl + "/recommendation/" + babyId;
    logger.info("Calling recommendation service: " + url);

    RecommendationResponse response = restTemplate.getForObject(url, RecommendationResponse.class);
    logger.info("Received recommendation response: " + response);

    return response.recommendGoodsIds();
  }
}
