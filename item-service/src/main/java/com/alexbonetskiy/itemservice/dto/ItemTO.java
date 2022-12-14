package com.alexbonetskiy.itemservice.dto;


import com.alexbonetskiy.itemservice.utils.BigDecimal2JsonDeserializer;
import com.alexbonetskiy.itemservice.utils.mapstruct.Default;
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

    @Default
    public ItemTO(Integer id, @NotBlank @Size(max = 30) String name, @NotBlank @Size(max = 256) String description, @Positive BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.exception = null;
    }

    public ItemTO(String exception) {
        this.id = null;
        this.name = null;
        this.description = null;
        this.price = null;
        this.exception = exception;

    }


}
