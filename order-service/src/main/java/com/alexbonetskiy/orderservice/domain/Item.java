package com.alexbonetskiy.orderservice.domain;


import com.alexbonetskiy.orderservice.utils.BigDecimal2JsonDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;

    @NotNull
    @Column(name = "catalog_id", nullable = false, updatable = false)
    private Integer catalogId;

    @Column(nullable = false, updatable = false)
    @NotBlank
    @Size(max = 30)
    private String name;

    @Column(nullable = false, updatable = false)
    @NotBlank
    @Size(max = 256)
    private String description;

    @Column(nullable = false, updatable = false)
    @NotNull
    @Positive
    @JsonDeserialize(using = BigDecimal2JsonDeserializer.class)
    private BigDecimal price;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<OrderItem> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return catalogId.equals(item.catalogId) && name.equals(item.name) && description.equals(item.description) && price.equals(item.price);
    }


    @Override
    public int hashCode() {
        return Objects.hash(catalogId, name, description, price);
    }

    @Override
    public String toString() {
        return "Item{" +
                "catalogId=" + catalogId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }


}
