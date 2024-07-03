package com.ptit.qldt.models;

//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;

import javax.persistence.*;
import java.util.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private int account_id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String gender;
    private Integer date;
    private Integer month;
    private Integer year;
    private Integer role;
    private String otp;


    public String generateAuthToken() {
        String secretKey = Env.get("JWTPRIVATEKEY");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withClaim("account_id", this.account_id)
                .withClaim("name", this.name)
                .withClaim("email", this.email)
                .withClaim("gender", this.gender)
                .withClaim("date", this.date)
                .withClaim("month", this.month)
                .withClaim("year", this.year)
                .withClaim("role", this.role)
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 ngày
                .sign(algorithm);

        return token;
    }
}
