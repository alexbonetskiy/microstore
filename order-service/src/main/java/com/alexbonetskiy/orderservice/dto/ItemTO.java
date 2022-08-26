package com.alexbonetskiy.orderservice.dto;

import com.alexbonetskiy.orderservice.utils.BigDecimal2JsonDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.istack.NotNull;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Value
public class ItemTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 100L;

    @NotNull
    Integer id;

    @NotBlank
    @Size(max = 30)
    String name;

    @NotBlank
    @Size(max = 256)
    String description;

    @NotNull
    @Positive
    @JsonDeserialize(using = BigDecimal2JsonDeserializer.class)
    BigDecimal price;

    String exception;

    @JsonCreator
    public ItemTO(@JsonProperty("id") Integer id,@JsonProperty("name") @NotBlank @Size(max = 30) String name,@JsonProperty("description") @NotBlank @Size(max = 256) String description, @JsonProperty("price")  @Positive BigDecimal price,@JsonProperty("exception") String exception) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.exception = exception;
    }
}
