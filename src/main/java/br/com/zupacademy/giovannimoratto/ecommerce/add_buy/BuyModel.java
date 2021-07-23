package br.com.zupacademy.giovannimoratto.ecommerce.add_buy;

import br.com.zupacademy.giovannimoratto.ecommerce.add_product.ProductModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_compras")
public class BuyModel {

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

    /* Constructors */
    // Default - JPA
    @Deprecated
    public BuyModel() {
    }

    public BuyModel(Integer quantity, ProductModel product, Gateway gateway, UserModel costumer) {
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

}
