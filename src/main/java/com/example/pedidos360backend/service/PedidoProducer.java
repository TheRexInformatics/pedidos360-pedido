package com.example.pedidos360backend.service;

import com.example.pedidos360backend.config.RabbitMQConfig;
import com.example.pedidos360backend.dto.EmailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class PedidoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void notificarCreacionPedido(String correoDestino, Long idPedido, Double total) {
        String asunto = "Confirmación de Pedido #" + idPedido + " - Pedidos360";
        String cuerpo = "¡Hola!\n\nTu pedido #" + idPedido + " por un total de $" + total + " ha sido procesado exitosamente.\n\n¡Gracias por tu compra!";

        EmailDTO emailDTO = new EmailDTO(correoDestino, asunto, cuerpo);

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, emailDTO);
        System.out.println(">>> [RABBITMQ PRODUCTOR] Evento de pedido #" + idPedido + " enviado a 'emailQueue'");
    }

    public void notificarDescuentoInventario(Long idPedido, Long productoId, Integer cantidad) {
        Map<String, Object> mensaje = Map.of(
            "idPedido", idPedido,
            "productoId", productoId,
            "cantidad", cantidad
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_INVENTARIO, mensaje);
        System.out.println(">>> [RABBITMQ PRODUCTOR] Descuento enviado a 'cola_inventario' para pedido #" + idPedido);
    }

    public void notificarPago(Long idPedido, Double total) {
        Map<String, Object> mensaje = Map.of(
            "idPedido", idPedido,
            "total", total,
            "estado", "PAGADO"
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.COLA_PAGOS, mensaje);
        System.out.println(">>> [RABBITMQ PRODUCTOR] Pago enviado a 'cola_pagos' para pedido #" + idPedido);
    }
}