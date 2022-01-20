package com.dasom.gongtalk.domain.user;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.util.RandomStringGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"username", "password", })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;


    @Setter
    private String deviceNum;

    @Setter
    @OneToOne
    private Setting setting;

    @Setter
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

