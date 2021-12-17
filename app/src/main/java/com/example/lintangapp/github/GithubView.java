package com.example.lintangapp.github;

public class GithubView {
    private String name;
    private String star;

    private final String nameS = "name: ";
    private final String starS = "star: ";

    public GithubView(String name, String star){
        this.name = name;
        this.star = star;
    }

    public String getName() {
        return this.nameS + name;
    }

    public String getStar() {
        return this.starS + star;
    }

}
