module com.example.immobilier {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires spring.boot;
    requires spring.context;
    requires spring.beans;
    requires spring.web;
    requires spring.data.commons;
    requires spring.data.jpa;
    requires spring.core;


    requires spring.boot.autoconfigure;
    requires com.fasterxml.jackson.databind;
    requires spring.tx;
    requires spring.aop;

    opens com.example.immobilier.Controller to javafx.fxml, spring.beans, spring.data.jpa, spring.data.commons,spring.context, spring.core, java.base;
    opens com.example.immobilier.Service to spring.beans, spring.data.jpa, spring.data.commons,spring.context, spring.core, java.base;
    opens com.example.immobilier.Entity to org.hibernate.orm.core, spring.core, java.base;
    opens com.example.immobilier to javafx.fxml,javafx.graphics, spring.core, spring.context, org.hibernate.orm.core, jakarta.persistence, java.base;

    exports com.example.immobilier;
    exports com.example.immobilier.Controller;
    exports com.example.immobilier.Service;
    exports com.example.immobilier.Entity;
    exports com.example.immobilier.Repository;
}