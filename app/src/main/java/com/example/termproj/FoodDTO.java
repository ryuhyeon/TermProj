package com.example.termproj;

public class FoodDTO {
    public String getContent_name() {
        return content_name;
    }

    public void setContent_name(String content_name) {
        this.content_name = content_name;
    }

    public int getTotal_star() {
        return total_star;
    }

    public void setTotal_star(int total_star) {
        this.total_star = total_star;
    }

    String content_name;
    int total_star;
}
