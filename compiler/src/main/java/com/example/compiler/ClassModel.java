package com.example.compiler;

import com.example.annotation.BindView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashSet;

import javax.annotation.processing.Filer;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

public class ClassModel {
    private HashSet<VariableElement> variableElements;
    private PackageElement packageElement;
    private TypeElement classElement;

    public ClassModel(PackageElement packageElement, TypeElement classElement) {
        this.packageElement = packageElement;
        this.classElement = classElement;
        variableElements = new HashSet<>();
    }

    public void addElement(VariableElement element) {
        variableElements.add(element);
    }

    public void generateJavaFile(Filer filer) {
        try {
            JavaFileObject jfo = filer.createSourceFile(classElement.getQualifiedName() + "$$view_binding");
            BufferedWriter bw = new BufferedWriter(jfo.openWriter());
            bw.append("package ").append(packageElement.getQualifiedName()).append(";\n");
            bw.newLine();
            bw.append(getImportString());
            bw.newLine();
            bw.append("public class ").append(classElement.getSimpleName()).append("$$view_binding implements Injectable {\n");
            bw.newLine();
            bw.append(getFiledString());
            bw.newLine();
            bw.append(getConstructString());
            bw.newLine();
            bw.append("}");
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成import代码
     */
    private String getImportString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("import android.view.View;\n");
        stringBuilder.append("import com.example.hao.learnself.date_2018_12_28.Injectable;\n");
        stringBuilder.append("import ").append(classElement.getQualifiedName()).append(";\n");
        HashSet<String> importStrs = new HashSet<>();
        for (VariableElement element : variableElements) {
            importStrs.add("import " + element.asType().toString() + ";\n");
        }
        for (String str : importStrs) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    /**
     * 生成成员变量
     */
    private String getFiledString() {
        return "private " + classElement.getSimpleName().toString() + " target;\n";
    }

    /**
     * 生成构造函数
     */
    private String getConstructString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("public ").append(classElement.getSimpleName().toString()).append("$$view_binding")
                .append("(").append(classElement.getSimpleName()).append(" target, ").append("View view) {\n");
        stringBuilder.append("this.target = target;\n");
        for (VariableElement element : variableElements) {
            int resId = element.getAnnotation(BindView.class).value();
            stringBuilder.append("target.").append(element.getSimpleName()).append(" = (").append(element.asType().toString())
                    .append(")view.findViewById(").append(resId).append(");\n");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
