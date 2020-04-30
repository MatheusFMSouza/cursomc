package br.com.matheus.cursomc.services;

import br.com.matheus.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfimationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);
}
