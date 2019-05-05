package vn.edu.vnuk.shopping.exception.email;

public class EmailFormatException extends Exception{

    public EmailFormatException(String email){
        super("EmailFormatException with email = " + email);
    }
}
