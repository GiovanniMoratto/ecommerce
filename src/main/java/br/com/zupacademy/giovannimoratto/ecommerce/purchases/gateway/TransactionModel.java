package br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway;

import br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.TransactionStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_transacao")
public class TransactionModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_transacao", nullable = false)
    private TransactionStatus status;
    @Column(name = "gateway_pagamento", nullable = false)
    private String gateway;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "id_compra", nullable = false)
    private PurchaseModel purchase;

    /* Constructors */
    // Default - JPA
    public TransactionModel() {
    }

    // Set Requests (PagseguroRequest.class or PaypalRequest.class) values in TransactionModel.class
    public TransactionModel(TransactionStatus status, String gateway, PurchaseModel purchase) {
        this.status = status;
        this.gateway = gateway;
        this.purchase = purchase;
    }

    // Check if Transaction Status is OK
    public boolean complete() {
        return this.status.equals(TransactionStatus.successful);
    }

}
