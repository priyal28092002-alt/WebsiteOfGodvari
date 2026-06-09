package com.godavari.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class WhatsAppService {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppService.class);
    private static final String WHATSAPP_SERVER = "http://localhost:9091";

    @Value("${whatsapp.provider:log-only}")
    private String provider;

    @Value("${whatsapp.company-name:Godavari Building Materials}")
    private String companyName;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String toPhone, String message) {
        if (toPhone == null || toPhone.isBlank()) {
            log.warn("==> WhatsApp: Cannot send - phone number is empty");
            return;
        }
        String phone = normalizePhone(toPhone);
        log.info("==> WhatsApp: Sending to {} via provider={}", phone, provider);

        switch (provider.toLowerCase()) {
            case "wppconnect" -> sendViaWppConnect(phone, message);
            default           -> logOnly(phone, message);
        }
    }

    private void sendViaWppConnect(String phone, String message) {
        try {
            log.info("==> =============================================");
            log.info("==> WHATSAPP SEND | to={}", phone);
            log.info("==> Message preview: {}", message.length() > 80 ? message.substring(0, 80) + "..." : message);
            log.info("==> =============================================");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String body = "{\"phone\":\"" + phone + "\",\"message\":\""
                    + message.replace("\\", "\\\\").replace("\"", "\\\"")
                             .replace("\n", "\\n").replace("\r", "")
                    + "\"}"; 
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    WHATSAPP_SERVER + "/send", entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("==> ✅ WHATSAPP SENT SUCCESSFULLY | to={}", phone);
            } else {
                log.error("==> ❌ WHATSAPP FAILED | to={} status={}", phone, response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("==> ❌ WHATSAPP ERROR | to={} | Is whatsapp-server running on port 9091? | error={}", phone, e.getMessage());
        }
    }

    private void logOnly(String phone, String message) {
        log.info("==> [LOG-ONLY] WhatsApp to={}", phone);
        log.info("==> [LOG-ONLY] Message:\n{}", message);
    }

    private String normalizePhone(String phone) {
        String cleaned = phone.replaceAll("[\\s\\-+()]", "");
        if (cleaned.startsWith("0")) cleaned = "91" + cleaned.substring(1);
        if (cleaned.length() == 10) cleaned = "91" + cleaned;
        return cleaned;
    }
}
