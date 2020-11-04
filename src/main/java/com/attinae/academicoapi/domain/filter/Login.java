package com.attinae.academicoapi.domain.filter;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Login {

    @NotBlank
    private String usuario;

    @NotBlank
    private String password;
}
