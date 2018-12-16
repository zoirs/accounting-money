package com.accountingmoney.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    private long id;

    @Column(name = "owner", length = 64)
    private String owner;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "number", nullable = false)
    private String number;

    @SuppressWarnings("unused")
    protected Account() {
    }

    public Account(String owner, BigDecimal amount) {
        super();
        this.owner = owner;
        this.amount = amount;
        this.number = UUID.randomUUID().toString();
    }

    public void withdraw(BigDecimal money) {
        amount = amount.subtract(money);
    }

    public void deposit(BigDecimal money) {
        amount = amount.add(money);
    }

    public long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isOnAccount(BigDecimal amount) {
        return this.amount.compareTo(amount) < 0;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
