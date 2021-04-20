package com.openclassrooms.webapp.model;

import lombok.Data;

@Data
// @data permet de générer automatiquement les getters et setters pour chaque attribut.
public class Employee {

    private Integer id;

    private String firstName;

    private String lastName;

    private String mail;

    private String password;

}
