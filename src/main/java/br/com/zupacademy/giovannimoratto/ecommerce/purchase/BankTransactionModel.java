package br.com.zupacademy.giovannimoratto.ecommerce.purchase;

import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.Gateway;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_request.PurchaseModel;
import br.com.zupacademy.giovannimoratto.ecommerce.purchase_response.TransactionStatus;
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
public class BankTransactionModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @NotNull
    private TransactionStatus status;
    @NotNull
    @Enumerated
    private Gateway gateway;
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne
    @NotNull
    @ExistsId(domainClass = PurchaseModel.class)
    private PurchaseModel purchase;

    /* Constructors */
    // Default - JPA
    public BankTransactionModel() {
    }

    public BankTransactionModel(TransactionStatus status, String idTransacaoGateway, PurchaseModel purchase) {
        this.status = status;
        this.idTransacaoGateway = idTransacaoGateway;
        this.purchase = purchase;
    }

    public boolean complete() {
        return this.status.equals(TransactionStatus.successful);
    }

}
