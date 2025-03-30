package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.example.dto.WhatsAppRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@RestController
@CrossOrigin

public class WhatsAppController {
//    @Value("${infosip.key}")
//    private String apiKey;

    @Value("${infobip.base.url}")
    private String baseUrl;

    @Value("${infobip.whatsapp.sender}")

    private   String senderNumber;


    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody WhatsAppRequest whatsAppRequest) throws IOException, InterruptedException {
        if (whatsAppRequest.getFrom() == null) {
            whatsAppRequest.setFrom("447860099299");
        }

        System.out.println(whatsAppRequest);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://9k3v4d.api.infobip.com/whatsapp/1/message/text"))

                .header("Authorization", "App 4cd10a375b74d2edf8ec12ea7e7f0f4a-ee56a346-98cd-4e00-b58b-37cd37fe3f0c")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(whatsAppRequest)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response: " + response.body());

        return ResponseEntity.ok(response.body());
    }
}
