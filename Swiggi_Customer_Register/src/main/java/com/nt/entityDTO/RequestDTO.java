package com.nt.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDTO {
    
    private Long id; 
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long mobile;
}

