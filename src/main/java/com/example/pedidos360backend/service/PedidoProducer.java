package com.example.pedidos360backend.service;

import com.example.pedidos360backend.config.RabbitMQConfig;
import com.example.pedidos360backend.dto.EmailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notificarCreacionPedido(String correoDestino, Long idPedido, Double total) {
        String asunto = "Confirmación de Pedido #" + idPedido + " - Pedidos360";
        String cuerpo = "¡Hola!\n\nTu pedido #" + idPedido + " por un total de $" + total + " ha sido procesado exitosamente.\n\n¡Gracias por tu compra!";

        EmailDTO emailDTO = new EmailDTO(correoDestino, asunto, cuerpo);

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, emailDTO);
        System.out.println(">>> [RABBITMQ PRODUCTOR] Evento de pedido #" + idPedido + " enviado a la cola 'emailQueue'");
    }
}