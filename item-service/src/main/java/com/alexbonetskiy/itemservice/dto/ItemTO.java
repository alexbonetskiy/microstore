package com.alexbonetskiy.itemservice.dto;


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
    BigDecimal price;

}
