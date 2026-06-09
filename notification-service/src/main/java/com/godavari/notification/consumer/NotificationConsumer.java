package com.godavari.notification.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.godavari.notification.config.KafkaTopicConfig;
import com.godavari.notification.dto.InvoicePaidEvent;
import com.godavari.notification.dto.OrderPlacedEvent;
import com.godavari.notification.service.MessageTemplateService;
import com.godavari.notification.service.WhatsAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    @Autowired private WhatsAppService whatsAppService;
    @Autowired private MessageTemplateService templateService;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    /** ── Listen for Order Placed events ── */
    @KafkaListener(
        topics = KafkaTopicConfig.ORDER_PLACED_TOPIC,
        groupId = "notification-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderPlaced(String message) {
        log.info("==> KAFKA RECEIVED | topic={} msg={}", KafkaTopicConfig.ORDER_PLACED_TOPIC,
                message.length() > 100 ? message.substring(0, 100) + "..." : message);
        try {
            OrderPlacedEvent event = mapper.readValue(message, OrderPlacedEvent.class);
            log.info("==> ORDER PLACED EVENT | orderNumber={} customer={} phone={}",
                    event.getOrderNumber(), event.getCustomerName(), event.getCustomerPhone());

            String whatsappMsg = templateService.buildOrderPlacedMessage(event);
            whatsAppService.sendMessage(event.getCustomerPhone(), whatsappMsg);

            log.info("==> ORDER NOTIFICATION SENT | orderNumber={}", event.getOrderNumber());
        } catch (Exception e) {
            log.error("==> ORDER NOTIFICATION FAILED | error={}", e.getMessage(), e);
        }
    }

    /** ── Listen for Invoice Paid events ── */
    @KafkaListener(
        topics = KafkaTopicConfig.INVOICE_PAID_TOPIC,
        groupId = "notification-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleInvoicePaid(String message) {
        log.info("==> KAFKA RECEIVED | topic={}", KafkaTopicConfig.INVOICE_PAID_TOPIC);
        try {
            InvoicePaidEvent event = mapper.readValue(message, InvoicePaidEvent.class);
            log.info("==> INVOICE PAID EVENT | invoiceNumber={} customer={} phone={} amount={}",
                    event.getInvoiceNumber(), event.getCustomerName(),
                    event.getCustomerPhone(), event.getTotalAmount());

            String whatsappMsg = templateService.buildInvoicePaidMessage(event);
            whatsAppService.sendMessage(event.getCustomerPhone(), whatsappMsg);

            log.info("==> INVOICE NOTIFICATION SENT | invoiceNumber={}", event.getInvoiceNumber());
        } catch (Exception e) {
            log.error("==> INVOICE NOTIFICATION FAILED | error={}", e.getMessage(), e);
        }
    }
}
