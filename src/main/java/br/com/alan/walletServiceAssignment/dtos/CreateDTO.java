package br.com.alan.walletServiceAssignment.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDTO {

    @NotNull
    @JsonProperty("client-id")
    UUID clientId;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    @JsonProperty("wallet-name")
    String walletName;
}
