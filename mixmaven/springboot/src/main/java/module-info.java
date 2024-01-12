module mixmaven.springboot {
    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.boot.autoconfigure;
    requires com.google.gson;

    requires mixmaven.core;
    requires mixmaven.json;

    opens springboot to spring.beans, spring.context, spring.web, spring.core;
}