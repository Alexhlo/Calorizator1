module Calorizator {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.controls;
    requires java.sql;

    opens main;
    opens main.controllers;
    opens main.controllers.productControllers;
    opens main.entity;
    opens main.entity.enums;
    opens main.service;
    opens main.service.impls;
    opens main.utils;
    opens main.utils.animations;
}