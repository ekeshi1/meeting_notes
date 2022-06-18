package com.example.meeting_notes.mail;


import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    Configuration config;
    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("erald97@ut.ee");
        message.setTo(to);
        message.setSubject(subject);
        message.setText("text");
        emailSender.send(message);

    }

    public void sendMeetingSummaryEmail(EMail mail) {
        MimeMessage mimeMessage =emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = null;
            try {
                mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            mimeMessageHelper.setSubject(mail.getSubject());
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.setTo(mail.getTo());
            mail.setContent(geContentFromTemplate(mail.getModel()));
            mimeMessageHelper.setText(mail.getContent(), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String geContentFromTemplate(Map< String, Object > model) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        config.getTemplate("mail-template.flt").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

}
