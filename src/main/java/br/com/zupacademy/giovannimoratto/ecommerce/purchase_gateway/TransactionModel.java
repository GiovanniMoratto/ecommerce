package br.com.zupacademy.giovannimoratto.ecommerce.purchase_gateway;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_costumer.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.validations.annotations.ExistsId;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Column(name = "ID")
    private Long id;
    //@Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TRANSACAO", nullable = false)
    private TransactionStatus status;
    @Column(name = "GATEWAY_PAGAMENTO", nullable = false)
    private String gateway;
    @CreationTimestamp
    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "ID_COMPRA", nullable = false)
    private PurchaseModel purchase;

    /* Constructors */
    // Default - JPA
    public TransactionModel() {
    }

    public TransactionModel(TransactionStatus status, String gateway, PurchaseModel purchase) {
        this.status = status;
        this.gateway = gateway;
        this.purchase = purchase;
    }

    public boolean complete() {
        return this.status.equals(TransactionStatus.successful);
    }

}
