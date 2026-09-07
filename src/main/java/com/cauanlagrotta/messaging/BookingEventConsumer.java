package com.cauanlagrotta.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import com.cauanlagrotta.model.PaymentOrder;
import com.cauanlagrotta.service.BookingService;

import lombok.RequiredArgsConstructor;

@Configuration 
@RequiredArgsConstructor 
public class BookingEventConsumer {

    private final BookingService bookingService;

    @RabbitListener(queues = "booking-queue")
    public void bookingUpdateListener(PaymentOrder paymentOrder){
        bookingService.bookingSuccess(paymentOrder);
    }
}
