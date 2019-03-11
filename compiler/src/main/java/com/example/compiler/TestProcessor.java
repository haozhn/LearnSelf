package com.example.compiler;

import com.example.annotation.BindViewCompiler;
import com.example.annotation.OnClickCompiler;
import com.example.annotation.TestCompiler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

public class TestProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(TestCompiler.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(TestCompiler.class)) {
//            System.out.println("is ExecutableElement  "+(element instanceof ExecutableElement));
//            System.out.println("is PackageElement  "+(element instanceof PackageElement));
//            System.out.println("is TypeElement  "+(element instanceof TypeElement));
//            System.out.println("is VariableElement  "+(element instanceof VariableElement));
//            System.out.println("is TypeParameterElement  "+(element instanceof TypeParameterElement));
//            System.out.println(element.getSimpleName());
//            System.out.println(Arrays.toString(element.getModifiers().toArray()));
//            System.out.println(element.getEnclosingElement().getSimpleName());
//            System.out.println(Arrays.toString(element.getEnclosingElement().getEnclosedElements().toArray()));
//            System.out.println(element.getEnclosingElement().getEnclosingElement().getSimpleName());
//            System.out.println(Arrays.toString(element.getEnclosingElement().getEnclosingElement().getEnclosedElements().toArray()));
//            System.out.println(element.getEnclosingElement().getEnclosingElement().getEnclosingElement().getSimpleName());
//            System.out.println(Arrays.toString(element.getEnclosingElement().getEnclosingElement().getEnclosingElement().getEnclosedElements().toArray()));
//            System.out.println(element.getEnclosingElement().getEnclosingElement().getEnclosingElement().getEnclosingElement());
//            System.out.println(Arrays.toString(element.getEnclosingElement().getEnclosedElements().toArray()));
//            System.out.println(element.asType().toString());
//            System.out.println(Arrays.toString(processingEnv.getElementUtils().getAllMembers((TypeElement) element).toArray()));
//            System.out.println(processingEnv.getElementUtils().getBinaryName((TypeElement) element));
//            System.out.println(processingEnv.getElementUtils().getPackageOf(element).getSimpleName());
//            System.out.println(processingEnv.getTypeUtils().getDeclaredType((TypeElement) element));
//            try {
//                JavaFileObject jfo =  processingEnv.getFiler().createSourceFile("Test");
//                BufferedWriter bw = new BufferedWriter(jfo.openWriter());
//                bw.append("public class Test {}");
//                bw.flush();
//                bw.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return false;
    }
}
