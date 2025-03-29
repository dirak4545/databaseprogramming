//package com.example.demo.webclient;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Service
//public class PublicApiService {
//
//    private final Webclient webclient;
//
//    @Value("${api.public-key}")
//    private String apiKey;
//
//    public PublicApiService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("https://api.example.com").build();
//    }
//
//    public String fetchData() {
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder.path("/data").queryParam("key", apiKey).build())
//                .retrieve()
//                .bodyToMono(String.class)
//                .block(); // 비동기 처리를 원하면 block() 대신 구독(subscribe) 사용
//    }
//}
