package com.example.compiler;

import com.example.annotation.BindView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
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
        classMap = new HashMap<>();
        System.out.println(processingEnvironment.getOptions().get("MODULE_NAME"));
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

    private HashMap<String, ClassModel> classMap;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        classMap.clear();
        Set<? extends Element> re = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : re) {
            TypeElement classElement = (TypeElement) element.getEnclosingElement();
            String qualifiedName = classElement.getQualifiedName().toString();
            ClassModel model = classMap.get(qualifiedName);
            if (model == null) {
                model = new ClassModel(elementUtil.getPackageOf(classElement), classElement);
                classMap.put(qualifiedName, model);
            }
            model.addElement((VariableElement) element);
        }

        for (ClassModel model : classMap.values()) {
            model.generateJavaFile(filer);
        }
        return true;
    }

    @Override
    public Set<String> getSupportedOptions() {
        HashSet<String> set = new HashSet<>();
        set.add("MODULE_NAME");
        return set;
    }

    private void note(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
