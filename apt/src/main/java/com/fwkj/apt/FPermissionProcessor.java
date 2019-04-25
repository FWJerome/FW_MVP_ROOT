package com.fwkj.apt;

import com.fwkj.annotation.FPermission;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description :
 */
@AutoService(Processor.class)
public class FPermissionProcessor extends AbstractProcessor {
    private Types typeUtils;
    /**
     * 元素相关的辅助类
     */
    private Elements elementUtils;
    /**
     * 文件相关的辅助类
     */
    private Filer filer;
    /**
     * 日志相关的辅助类
     */
    private Messager messager;

    /**
     * 在这个方法里面你必须指定哪些注解应该被注解处理器注册。注意，它的返回值是一个String集合，包含了你的注解处理器想要处理的注解类型的全称。换句话说，你在这里定义你的注解处理器要处理哪些注解,就是自定义的注解如 FPermission
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(FPermission.class.getCanonicalName());
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
        String methodName = "";
        //扫描所有被FPermission注解的类列表
        //初始化每个文件对应的键值对 key为class的Name value为方法集
        HashMap<String, List<MethodSpec>> fileMaps = new HashMap<>();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(FPermission.class)) {
            if (annotatedElement.getKind() != ElementKind.METHOD) {
                error(annotatedElement, "Only classes can be annotated with @%s", FPermission.class.getSimpleName());
                return true;
            }
            // 获取声明element的全限定类名 (com.zhangke.simplifybutterknife.MainActivity)
            TypeElement typeElement = (TypeElement) annotatedElement.getEnclosingElement();
            // 获取包名
            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            //获取方法名
            methodName = annotatedElement.getSimpleName().toString();
            // 根据旧Java类名创建新的Java文件
            String className = typeElement.getQualifiedName().toString().substring(packageName.length() + 1);

            //初始化要导入的相关包
            ClassName permissiondialogclass = ClassName.get("com.fwkj.fw_root_library.utils", "PermissionDialog");
            ClassName activity = ClassName.get(packageName, className);

            //获取值
            FPermission fPermission = annotatedElement.getAnnotation(FPermission.class);
            String[] valuesArr = fPermission.values();
            String join = String.join(",", valuesArr);

            //判断是否有这个类存在
            if (!fileMaps.containsKey(className)) {
                //没有这个类就新建一个,并且初始化
                fileMaps.put(className, new ArrayList<>());
            }
            fileMaps.get(className).add(createMethod(activity, methodName, permissiondialogclass, join));
        }
        //循环处理好所有的方法以后就遍历创建文件
        for (Map.Entry<String, List<MethodSpec>> entry : fileMaps.entrySet()) {
            createFile(entry.getKey() + "_PermissionUtils", entry.getValue());
        }
        return false;
    }

    /**
     * 创建并且返回一个方法
     *
     * @param activity              方法参数
     * @param methodName            方法名
     * @param permissiondialogclass
     * @param join                  获取到的注解值
     */
    private MethodSpec createMethod(ClassName activity, String methodName, ClassName permissiondialogclass, String join) {
        //权限方法生成
        MethodSpec methodSpec = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(activity, "context", Modifier.FINAL)
                .addStatement("new $T() {\n" +
                        "            @Override\n" +
                        "            public void hasThis() {\n" +
                        "                context." + methodName + "();\n" +
                        "            }\n" +
                        "        }.getPermission(context, $S)", permissiondialogclass, join)
                .build();
        return methodSpec;
    }

    private synchronized void createFile(String s, List<MethodSpec> methodSpecList) {
        TypeSpec typeSpec = TypeSpec.classBuilder(s)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethods(methodSpecList)
                .addJavadoc("@  Auto Create")
                .build();
        JavaFile javaFile = JavaFile.builder("com.fwkj.apt", typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);

    }
}
