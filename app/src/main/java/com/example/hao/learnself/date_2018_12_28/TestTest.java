package com.example.hao.learnself.date_2018_12_28;

import com.example.hao.learnself.date_2018_12_28.annotation.TestAnnotation;

@TestAnnotation
public class TestTest<@TestAnnotation T> {

    @TestAnnotation
    public int num;

    @TestAnnotation
    public T param;

    @TestAnnotation
    public TestTest(@TestAnnotation int num, @TestAnnotation T param) {
        @TestAnnotation String hello = "hello";
        this.num = num;
        this.param = param;
    }

    @TestAnnotation
    public int doSomething() {
        return 0;
    }

    @TestAnnotation
    public interface testInterface {

    }

    @TestAnnotation
    public enum testEnum {

    }
}
