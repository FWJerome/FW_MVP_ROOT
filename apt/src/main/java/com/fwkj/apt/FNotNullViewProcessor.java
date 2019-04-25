package com.fwkj.apt;

import com.fwkj.annotation.FNotNullView;
import com.fwkj.annotation.FPermission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;

public class FNotNullViewProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    /**
     * 在这个方法里面你必须指定哪些注解应该被注解处理器注册。注意，它的返回值是一个String集合，包含了你的注解处理器想要处理的注解类型的全称。换句话说，你在这里定义你的注解处理器要处理哪些注解,就是自定义的注解如 FNotNullView
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(FNotNullView.class.getCanonicalName());
        return annotataions;
    }

    /**
     * 用来指定你使用的 java 版本。通常你应该返回SourceVersion.latestSupported() 。不过，如果你有足够的理由坚持用 java 6 的话，你也可以返回SourceVersion.RELEASE_6。我建议使用SourceVersion.latestSupported()。在 Java 7 中，你也可以使用注解的方式来替代重写
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 所有的注解处理器类都必须有一个无参构造函数。然而，有一个特殊的方法init()，它会被注解处理工具调用，以ProcessingEnvironment作为参数。ProcessingEnvironment 提供了一些实用的工具类Elements, Types和Filer
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //扫描所有的被注解的类
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(FPermission.class)) {
            if (annotatedElement.getKind() == ElementKind.METHOD) {
                error(annotatedElement, "Only classes can be annotated with @%s", FPermission.class.getSimpleName());
                return true;
            }
            // 获取声明element的全限定类名 (com.zhangke.simplifybutterknife.MainActivity)
            TypeElement typeElement = (TypeElement) annotatedElement.getEnclosingElement();
            //获取文件位置
            JavaFileManager.Location location = new JavaFileManager.Location() {
                @Override
                public String getName() {
                    return typeElement.getQualifiedName().toString();
                }

                @Override
                public boolean isOutputLocation() {
                    return false;
                }
            };
            try {
                FileObject resource = filer.getResource(location, null, null);
                BufferedReader reader = new BufferedReader(resource.openReader(false));
                String s = "";
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);

    }
}


