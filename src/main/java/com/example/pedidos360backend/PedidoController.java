package com.example.pedidos360backend;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    
    private final List<String> pedidos = new ArrayList<>();

    @GetMapping
    public List<String> obtenerPedidos() {
        return pedidos;
    }

    @PostMapping
    public String crearPedido(@RequestBody String pedido) {
        pedidos.add(pedido);
        return "Añadido al carrito";
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