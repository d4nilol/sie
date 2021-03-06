package br.iesb.sie.service;

import br.iesb.sie.dto.EmailDTO;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class EmailService extends BaseService {

    /**
     * 
     */
    private static final long serialVersionUID = -1861716969829676681L;

    @Resource(mappedName = "java:jboss/mail/sie")
    private Session mailSession;

    @Inject
    private Logger logger;

    @Asynchronous
    public void enviarEmail(EmailDTO emailDTO) {
        try {
            Transport.send(emailDTO.pupularEmail(mailSession));
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Não foi possível enviar o email", e);
        }
    }

}
