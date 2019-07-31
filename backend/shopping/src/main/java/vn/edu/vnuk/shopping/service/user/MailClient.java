package vn.edu.vnuk.shopping.service.user;

public interface MailClient {

    void prepareAndSend(String recipient, String subject,String message);

}