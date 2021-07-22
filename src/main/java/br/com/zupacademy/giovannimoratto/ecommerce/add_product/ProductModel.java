package br.com.zupacademy.giovannimoratto.ecommerce.add_product;

import br.com.zupacademy.giovannimoratto.ecommerce.add_category.CategoryModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features.FeatureModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_images.ImageModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_question.QuestionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_product_review.ReviewModel;
import br.com.zupacademy.giovannimoratto.ecommerce.add_user.UserModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author giovanni.moratto
 */

@Entity
@Table(name = "tb_produtos")
public class ProductModel {

    /* Attributes */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "PRECO", nullable = false)
    private BigDecimal price;
    @Column(name = "QTA_DISPONIVEL", nullable = false)
    private Integer availableQuantity;
    @Column(name = "DESCRICAO", nullable = false)
    private String description;
    @Valid
    @ManyToOne
    @JoinColumn(name = "CATEGORIA", nullable = false)
    private CategoryModel category;
    @Valid
    @ManyToOne
    @JoinColumn(name = "USUARIO_CRIADOR", nullable = false)
    private UserModel userCreator;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set <FeatureModel> features = new HashSet <>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set <ImageModel> images = new HashSet <>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set <ReviewModel> reviews = new HashSet <>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    @OrderBy("titulo asc")
    private SortedSet <QuestionModel> questions = new TreeSet <>();

    /* Constructors */
    // Default - JPA
    @Deprecated
    public ProductModel() {
    }

    // Set ProductRequest.class values in ProductModel.class
    public ProductModel(String name, BigDecimal price, Integer availableQuantity, String description,
                        CategoryModel category, UserModel userCreator,
                        Collection <FeatureRequest> features) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.description = description;
        this.category = category;
        this.userCreator = userCreator;
        this.features.addAll(features.stream()
                .map(feature -> feature.toModel(this))
                .collect(Collectors.toSet()));
    }

    /* Methods */
    public void addImages(Set <String> imageLinks) {
        Set <ImageModel> images =
                imageLinks.stream().map(link -> new ImageModel(link, this)).collect(Collectors.toSet());
        this.images.addAll(images);
    }

    public <T> Set <T> mapFeatures(Function <FeatureModel, T> mapFunction) {
        return this.features.stream().map(mapFunction).collect(Collectors.toSet());
    }

    public <T> Set <T> mapImages(Function <ImageModel, T> mapFunction) {
        return this.images.stream().map(mapFunction).collect(Collectors.toSet());
    }

    public <T extends Comparable <T>> SortedSet <T> mapQuestions(Function <QuestionModel, T> mapFunction) {
        return this.questions.stream().map(mapFunction).collect(Collectors.toCollection(TreeSet::new));
    }

    /* Getters and Setters */
    public UserModel getUserCreator() {
        return userCreator;
    }

}