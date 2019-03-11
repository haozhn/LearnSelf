package com.example.compiler;

import com.example.annotation.BindViewCompiler;
import com.example.annotation.OnClickCompiler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class BindViewProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        classMap = new HashMap<>();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindViewCompiler.class.getCanonicalName());
        types.add(OnClickCompiler.class.getCanonicalName());
        return types;
    }

    private HashMap<String, ClassModel> classMap;
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 因为扫描会有多轮，所以需要清空一下
        classMap.clear();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindViewCompiler.class)) {
            ClassModel model = checkModel(element);
            model.addVariableElement((VariableElement) element);
        }

        for (Element element : roundEnvironment.getElementsAnnotatedWith(OnClickCompiler.class)) {
            ClassModel model = checkModel(element);
            model.addExecutableElement((ExecutableElement) element);
        }

        for (ClassModel model : classMap.values()) {
            model.generateJavaFile(processingEnv.getFiler());
        }
        return true;
    }

    private ClassModel checkModel(Element element) {
        // 获取当前类
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String qualifiedName = classElement.getQualifiedName().toString();
        // 查看是否已经保存在classMap中了，如果没有就新创建一个
        ClassModel model = classMap.get(qualifiedName);
        if (model == null) {
            model = new ClassModel(classElement);
            classMap.put(qualifiedName, model);
        }
        return model;
    }

    private void note(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
