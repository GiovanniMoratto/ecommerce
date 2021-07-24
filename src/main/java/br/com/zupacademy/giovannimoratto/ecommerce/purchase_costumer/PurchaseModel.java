package br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.TransactionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway.GatewayResponse;
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

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "QUANTIDADE", nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private ProductModel product;
    @NotNull
    @Enumerated
    @Column(name = "GATEWAY_PAGAMENTO", nullable = false)
    private Gateway gateway;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UserModel costumer;
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private final Set <TransactionModel> transactions = new HashSet <>();

    /* Constructors */
    // Default - JPA
    @Deprecated
    public PurchaseModel() {
    }

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

    public void addTransaction(GatewayResponse gateway) {
        TransactionModel newTransaction = gateway.create(this);
        this.transactions.add(newTransaction);
    }

    private Set <TransactionModel> successfullyTransactions(){
        return this.transactions.stream().filter(TransactionModel::complete).collect(Collectors.toSet());
    }

    public boolean successfullyProcessed(){
        return !successfullyTransactions().isEmpty();
    }

}
