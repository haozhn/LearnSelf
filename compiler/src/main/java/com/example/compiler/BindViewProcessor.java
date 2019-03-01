package com.example.compiler;

import com.example.annotation.BindView;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

public class BindViewProcessor extends AbstractProcessor {
    private Types typeUtil;
    private Elements elementUtil;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        typeUtil = processingEnvironment.getTypeUtils();
        elementUtil = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> re = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : re) {
            VariableElement variableElement = (VariableElement) element;
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            PackageElement packageElement = elementUtil.getPackageOf(classElement);

            String className = classElement.getSimpleName().toString();
            String packageName = packageElement.getQualifiedName().toString();
            String variableName = variableElement.getSimpleName().toString();
            note(element, "className=" + className);
            note(element, "packageName=" + packageName);
            note(element, "variableName=" + variableName);
        }
        return false;
    }

    private void note(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
