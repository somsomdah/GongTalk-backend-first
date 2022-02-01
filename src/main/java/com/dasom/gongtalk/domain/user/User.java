package com.dasom.gongtalk.domain.user;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.util.RandomStringGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"username", "password", "boards" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;

    @NotNull
    @Column(name="device_num", unique = true)
    private String deviceNum;

    @OneToOne
    private Setting setting;

    @ManyToMany
    private List<Board> boards;

    public User(String deviceNum)
    {
        setDeviceNum(deviceNum);
        setUsername();
        setPassword();
    }

    public void setUsername()
    {
        RandomStringGenerator rsg = new RandomStringGenerator();
        DateFormat formatter = new SimpleDateFormat("yyMMdd");
        String usnm = rsg.generate(4,0);
        String date = formatter.format(new Date());
        this.username = (usnm + date);
    }

    public void setPassword()
    {
        RandomStringGenerator rsg = new RandomStringGenerator();
        String pswd = rsg.generate(8,8);
        this.password = (pswd);
    }

}

