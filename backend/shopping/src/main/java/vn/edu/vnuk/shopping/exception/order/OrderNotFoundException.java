package vn.edu.vnuk.shopping.exception.order;

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(Long orderId) {
        super("OrderNotFoundException with id = " + orderId);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
