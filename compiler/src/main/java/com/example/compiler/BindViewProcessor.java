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
        Elements elements = processingEnv.getElementUtils();
        classMap.clear();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindViewCompiler.class)) {
            ClassModel model = checkModel(element);
            model.addVariableElement((VariableElement) element);

            List<AnnotationMirror> list = (List<AnnotationMirror>) elements.getAllAnnotationMirrors(element);
            elements.
            System.out.println(Arrays.toString(list.toArray()));
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
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String qualifiedName = classElement.getQualifiedName().toString();
        ClassModel model = classMap.get(qualifiedName);
        if (model == null) {
            model = new ClassModel(processingEnv.getElementUtils().getPackageOf(classElement), classElement);
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
