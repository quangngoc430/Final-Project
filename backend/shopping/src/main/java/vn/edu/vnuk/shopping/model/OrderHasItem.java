package vn.edu.vnuk.shopping.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Order_Has_Item")
public class OrderHasItem {

    public OrderHasItem() {}

    public OrderHasItem(OrderHasItem orderHasItem, Item item) {
        this.id = orderHasItem.id;
        this.orderId = orderHasItem.orderId;
        this.itemId = orderHasItem.itemId;
        this.quantity = orderHasItem.quantity;
        this.updatedAt = orderHasItem.updatedAt;
        this.createdAt = orderHasItem.createdAt;
        this.item = item;
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "OrderID")
    private Long orderId;

    @NotNull
    @Column(name = "ItemID")
    private Long itemId;

    @NotNull
    @Column(name = "Quantity")
    private Long quantity;

    @NotNull
    @Column(name = "Price")
    private Long price;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "UpdatedAt")
    private Date updatedAt;

    @Transient
    private Ordering ordering;

    @Transient
    private Item item;

    @Transient
    private DiscountPrice discountPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getQuantity() {return quantity;}

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Ordering getOrdering() {
        return ordering;
    }

    public void setOrdering(Ordering ordering) {
        this.ordering = ordering;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Ordering getOrder() {
        return this.ordering;
    }

    public void setOrder(Ordering ordering) {
        this.ordering = ordering;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public DiscountPrice getDiscountPrice() {
        return this.discountPrice;
    }

    public void setDiscountPrice(DiscountPrice discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return "OrderHasItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", order=" + ordering +
                ", item=" + item +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
