package com.banco.email;

public record EmailDTO(Long id, Long idCliente, String cedula, String nombre, String correo,
                       Double saldoAnterior, Double monto, String fechaDeDeposito) {
}
