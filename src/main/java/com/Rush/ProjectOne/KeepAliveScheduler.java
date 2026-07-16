// package com.Rush.ProjectOne;

// import lombok.extern.slf4j.Slf4j;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;

// @Slf4j
// @Component
// public class KeepAliveScheduler {
//     private static final String HEALTHCHECK_URL = "https://matches-wp7z.onrender.com/healthcheck";
//     @Scheduled(fixedRate = 30000)
//     public void pingBackend() {
//         try {
//             RestTemplate restTemplate = new RestTemplate();
//             restTemplate.getForObject(HEALTHCHECK_URL, String.class);
//             log.info("Pinged backend successfully!");
//         } catch (Exception e) {
//             log.error("Failed to ping backend: " + e.getMessage());
//         }
//     }
// }
