package br.com.zupacademy.giovannimoratto.ecommerce.purchases.costumer;

import br.com.zupacademy.giovannimoratto.ecommerce.products.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.TransactionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchases.gateway.request.GatewayResponse;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_compras")
public class PurchaseModel {

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private final Set <TransactionModel> transactions = new HashSet <>();
    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "quantidade", nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private ProductModel product;
    @NotNull
    @Enumerated
    @Column(name = "gateway_pagamento", nullable = false)
    private Gateway gateway;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UserModel costumer;

    /* Constructors */
    // Default - JPA
    @Deprecated
    public PurchaseModel() {
    }

    // Set CostumerController.class values in PurchaseModel.class
    public PurchaseModel(Integer quantity, ProductModel product, Gateway gateway, UserModel costumer) {
        this.quantity = quantity;
        this.product = product;
        this.gateway = gateway;
        this.costumer = costumer;
    }

    /* Methods */
    public String urlRedirect(UriComponentsBuilder uriBuilder) {
        return gateway.urlResponse(this, uriBuilder);
    }

    public void addTransaction(GatewayResponse gateway) {
        TransactionModel newTransaction = gateway.create(this);
        this.transactions.add(newTransaction);
    }

    public boolean successfullyProcessed() {
        return !successfullyTransactions().isEmpty();
    }

    private Set <TransactionModel> successfullyTransactions() {
        return this.transactions.stream().filter(TransactionModel::complete).collect(Collectors.toSet());
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductModel getProduct() {
        return product;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public UserModel getCostumer() {
        return costumer;
    }

}
