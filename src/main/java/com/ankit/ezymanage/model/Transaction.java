package com.ankit.ezymanage.model;

import java.util.Date;

// payment_id=MOJO1b16C05N14535322&
// payment_status=Credit&
// id=27b98e860542478b8b090f24e2b57932&
// transaction_id=df4175b9-bda5-47c9-a260-80447c860a82
// PaymentOrder{id='108f a8c38ec94f38ac707f0428dadf94', transactionId='f6435e75-95d c-4ec e-9f fc-5 b7da3b78456', status='completed', currency='INR', amount=10.0, name='student1', email='guptacare18 @gmail.com', phone='+919204040100', description='Instapay', webhookUrl='null', redirectUrl='http://localhost:8080/shops/2/cart/2/checkout/success/', createdAt='2021-11-16T19:47:05.497305Z', resourceUri='https://test.instamojo.com/v2/gateway/orders/id:108fa8c38ec94f38ac707f0428dadf94/', payments=[Payment{id='MOJO1b16J05N14535324', status='successful'}, Payment{id='MOJO1b16305N14535323', status='failed'}]}
public class Transaction {
    private int id;
    private String mojoTransactionId;
    private String paymentStatus;
    private Date createdAt;
    private int shopId;
    private int orderId;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMojoTransactionId() {
        return mojoTransactionId;
    }

    public void setMojoTransactionId(String mojoTransactionId) {
        this.mojoTransactionId = mojoTransactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Transaction() {
    }

    public Transaction(int id, String mojoTransactionId, String paymentStatus, Date createdAt, int shopId, int orderId,
            int amount) {
        this.id = id;
        this.mojoTransactionId = mojoTransactionId;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.shopId = shopId;
        this.orderId = orderId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction [amount=" + amount + ", createdAt=" + createdAt + ", id=" + id + ", mojoTransactionId="
                + mojoTransactionId + ", orderId=" + orderId + ", paymentStatus=" + paymentStatus + ", shopId=" + shopId
                + "]";
    }

}
