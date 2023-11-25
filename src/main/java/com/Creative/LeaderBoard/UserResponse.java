package com.Creative.LeaderBoard;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserResponse {
    @JsonProperty("rank")
    private String rank;
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;
    @JsonProperty("score")
    private int score;

    @JsonProperty("time_submit")
    private Date time_submit;

    public UserResponse(Long id,String username, int score,Date time_submit, String rank) {
        this.id = id;
        this.username = username;
        this.score = score;
        this.time_submit = time_submit;
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public Date getTime_submit(){return time_submit;}
}
