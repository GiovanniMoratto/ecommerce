package br.com.zupacademy.giovannimoratto.ecommerce.products;

import br.com.zupacademy.giovannimoratto.ecommerce.categories.CategoryModel;
import br.com.zupacademy.giovannimoratto.ecommerce.images.ImageModel;
import br.com.zupacademy.giovannimoratto.ecommerce.products.product_features.FeatureModel;
import br.com.zupacademy.giovannimoratto.ecommerce.products.product_features.FeatureRequest;
import br.com.zupacademy.giovannimoratto.ecommerce.questions.QuestionModel;
import br.com.zupacademy.giovannimoratto.ecommerce.reviews.ReviewModel;
import br.com.zupacademy.giovannimoratto.ecommerce.users.UserModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "id")
    private Long id;
    @Column(name = "nome", nullable = false)
    private String name;
    @Column(name = "preco", nullable = false)
    private BigDecimal price;
    @Column(name = "qta_disponivel", nullable = false)
    private Integer stockInformation;
    @Column(name = "descricao", nullable = false)
    private String description;
    @Valid
    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private CategoryModel category;
    @Valid
    @ManyToOne
    @JoinColumn(name = "vendedor", nullable = false)
    private UserModel seller;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference
    private Set <FeatureModel> features = new HashSet <>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference
    private Set <ImageModel> images = new HashSet <>();
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference
    private Set <ReviewModel> reviews = new HashSet <>();
    @OrderBy("titulo asc")
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference
    private SortedSet <QuestionModel> questions = new TreeSet <>();

    /* Constructors */
    // Default - JPA
    @Deprecated
    public ProductModel() {
    }

    // Set ProductRequest.class values in ProductModel.class
    public ProductModel(String name, BigDecimal price, Integer stockInformation, String description,
                        CategoryModel category, UserModel seller, Collection <FeatureRequest> features) {
        this.name = name;
        this.price = price;
        this.stockInformation = stockInformation;
        this.description = description;
        this.category = category;
        this.seller = seller;
        this.features.addAll(features.stream().map(feature -> feature.toModel(this))
                .collect(Collectors.toSet()));
    }

    /* Methods */
    // Add images into this product
    public void addImages(Set <String> imageLinks) {
        Set <ImageModel> images =
                imageLinks.stream().map(link -> new ImageModel(link, this)).collect(Collectors.toSet());
        this.images.addAll(images);
    }

    // Link feature value Collection by FeatureModel primary key
    public <T> Set <T> mapFeatures(Function <FeatureModel, T> mapFunction) {
        return this.features.stream().map(mapFunction).collect(Collectors.toSet());
    }

    // Link Image value Collection by ImageModel primary key
    public <T> Set <T> mapImages(Function <ImageModel, T> mapFunction) {
        return this.images.stream().map(mapFunction).collect(Collectors.toSet());
    }

    // Get average likes in product reviews
    public double averageLikes() {
        Set <Integer> likes = mapReviews(ReviewModel::getLikes);
        OptionalDouble average = likes.stream().mapToInt(like -> like).average();
        return average.orElse(0.0);
    }

    // Link Reviews value Collection by ReviewModel primary key
    public <T> Set <T> mapReviews(Function <ReviewModel, T> mapFunction) {
        return this.reviews.stream().map(mapFunction).collect(Collectors.toSet());
    }

    // Get total product reviews
    public int numberOfReviews() {
        return this.reviews.size();
    }

    // Get Total likes of a product
    public int numberOfLikes() {
        Set <Integer> likes = mapReviews(ReviewModel::getLikes);
        return likes.size();
    }

    // Link Questions value Sorted Collection by QuestionsModel primary key
    public <T extends Comparable <T>> SortedSet <T> mapQuestions(Function <QuestionModel, T> mapFunction) {
        return this.questions.stream().map(mapFunction).collect(Collectors.toCollection(TreeSet::new));
    }

    // Subtract the product quantity in the order from the stock
    public Boolean destock(Integer quantity) {
        if (quantity <= this.stockInformation) {
            stockInformation -= quantity;
            return true;
        }
        return false;
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockInformation() {
        return stockInformation;
    }

    public String getDescription() {
        return description;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public UserModel getSeller() {
        return seller;
    }

    public Set <FeatureModel> getFeatures() {
        return features;
    }

}