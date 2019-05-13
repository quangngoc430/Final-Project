package vn.edu.vnuk.shopping.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "OrderHasItem")
public class OrderHasItem {

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
    @Column(name = "DiscountPriceID")
    private Long discountPriceId;

    @Column(name = "CreatedAt")
    private Date createdAt;

    @Column(name = "UpdatedAt")
    private Date updatedAt;

    @Transient
    private Order order;

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getDiscountPriceId() {
        return discountPriceId;
    }

    public void setDiscountPriceId(Long discountPriceId) {
        this.discountPriceId = discountPriceId;
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

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
                ", discountPriceId=" + discountPriceId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", order=" + order +
                ", item=" + item +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
