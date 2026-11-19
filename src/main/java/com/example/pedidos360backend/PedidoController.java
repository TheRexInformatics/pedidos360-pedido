package com.example.pedidos360backend;

import com.example.pedidos360backend.service.PedidoProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController {
    @Autowired
    private PedidoProducer pedidoProducer;
    
    private final List<String> pedidos = new ArrayList<>();

    @GetMapping
    public List<String> obtenerPedidos() {
        return pedidos;
    }

    @PostMapping
    public String crearPedido(@RequestBody String pedido) {
        pedidos.add(pedido);
        long idPedido = pedidos.size();
        
        pedidoProducer.notificarCreacionPedido("martinvtellez22@gmail.com", idPedido, 100.0);
        pedidoProducer.notificarDescuentoInventario(idPedido, 1L, 1);
        pedidoProducer.notificarPago(idPedido, 100.0);

        return "Añadido al carrito y eventos encolados en las 3 colas de RabbitMQ";
    }

    @PutMapping("/{id}")
    public String modificarPedido(@PathVariable int id, @RequestBody String pedidoModificado) {
        if (id >= 0 && id < pedidos.size()) {
            pedidos.set(id, pedidoModificado);
            return "Carrito actualizado";
        }
        return "Error: Pedido no encontrado";
    }

    @DeleteMapping("/{id}")
    public String eliminarPedido(@PathVariable int id) {
        if (id >= 0 && id < pedidos.size()) {
            pedidos.remove(id);
            return "Eliminado del carrito";
        }
        return "Error: Pedido no encontrado";
    }
}
