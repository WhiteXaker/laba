import java.util.Date;

/**
 * Created by omatikaya on 19/12/2016.
 */
public class Order {
    private int orderId;
    private int customerId;
    private Date orderDate;
    private int itemId;
    private OrderStatus orderStatus;
    private int size;
    private double price;
    private int quantity;
    private int trackingNumber;
    private Date departureDate;
    private String comment;

    public Order(int orderId, int customerId, Date orderDate, int itemId, OrderStatus orderStatus, int size,
                 double price, int quantity, int trackingNumber, Date departureDate, String comment) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.itemId = itemId;
        this.orderStatus = orderStatus;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.trackingNumber = trackingNumber;
        this.departureDate = departureDate;
        this.comment = comment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
