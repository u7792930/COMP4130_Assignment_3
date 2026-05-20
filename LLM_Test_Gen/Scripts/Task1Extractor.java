import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Task1Extractor {

    private static final String[] TARGET_FILES = {

            // Commons Codec
            "Codec_18_buggy/src/main/java/org/apache/commons/codec/binary/StringUtils.java",

            // Commons Collections
            "Collections_27_buggy/src/main/java/org/apache/commons/collections4/map/MultiValueMap.java",

            // Commons Compress
            "Compress_45_buggy/src/main/java/org/apache/commons/compress/archivers/tar/TarUtils.java"
    };

    private static final String OUTPUT_CSV =
            "LLM_Test_Gen/Data/task1_output.csv";

    public static void main(String[] args) {

        try {

            CSVWriter writer =
                    new CSVWriter(new FileWriter(OUTPUT_CSV));

            // CSV Header
            String[] header = {
                    "FQN",
                    "MethodName",
                    "MethodSignature",
                    "ReturnType",
                    "Modifiers",
                    "Parameters",
                    "ThrownExceptions",
                    "MethodInvocations",
                    "Comments",
                    "SourceCode"
            };

            writer.writeNext(header);

            // Process target files
            for (String filePath : TARGET_FILES) {

                File javaFile = new File(filePath);

                if (!javaFile.exists()) {
                    System.out.println("File not found: " + filePath);
                    continue;
                }

                processJavaFile(javaFile, writer);
            }

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processJavaFile(File javaFile,
                                        CSVWriter writer) throws Exception {

        CompilationUnit cu =
                StaticJavaParser.parse(javaFile);

        // PACKAGE NAME
        String packageName =
                cu.getPackageDeclaration()
                        .map(pkg -> pkg.getNameAsString())
                        .orElse("");

        // CLASS NAME
        List<ClassOrInterfaceDeclaration> classes =
                cu.findAll(ClassOrInterfaceDeclaration.class);

        for (ClassOrInterfaceDeclaration clazz : classes) {

            String className =
                    clazz.getNameAsString();

            // GET ALL METHODS
            List<MethodDeclaration> methods =
                    clazz.getMethods();

            for (MethodDeclaration method : methods) {

                extractMethodInformation(
                        packageName,
                        className,
                        method,
                        writer
                );
            }
        }
    }


    private static void extractMethodInformation(
            String packageName,
            String className,
            MethodDeclaration method,
            CSVWriter writer
    ) {

        try {

            // FQN

            String fqn =
                    packageName + "." +
                            className + "." +
                            method.getNameAsString();

            // Method name

            String methodName =
                    method.getNameAsString();

            // Method signature

            String methodSignature =
                    method.getDeclarationAsString();


            // Return type

            String returnType =
                    method.getType().asString();


            // Modifiers

            String modifiers =
                    method.getModifiers()
                            .stream()
                            .map(modifier ->
                                    modifier.getKeyword().asString())
                            .collect(Collectors.joining(" "));

            // Parameters

            String parameters =
                    method.getParameters()
                            .stream()
                            .map(parameter ->
                                    parameter.getType() + " " +
                                            parameter.getNameAsString())
                            .collect(Collectors.joining(", "));


            // Thrown exceptions

            String thrownExceptions =
                    method.getThrownExceptions()
                            .stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", "));


            // method invocations


            String methodInvocations =
                    method.findAll(MethodCallExpr.class)
                            .stream()
                            .map(MethodCallExpr::getNameAsString)
                            .distinct()
                            .collect(Collectors.joining(", "));


            // Comments

            String comments =
                    method.getComment()
                            .map(comment ->
                                    cleanText(comment.getContent()))
                            .orElse("");


            // Source code

            String sourceCode =
                    cleanText(method.toString());




            String[] row = {
                    fqn,
                    methodName,
                    methodSignature,
                    returnType,
                    modifiers,
                    parameters,
                    thrownExceptions,
                    methodInvocations,
                    comments,
                    sourceCode
            };

            writer.writeNext(row);

            System.out.println("Extracted: " + fqn);

        } catch (Exception e) {

            System.out.println(
                    "Failed to extract method: "
                            + method.getNameAsString()
            );

            e.printStackTrace();
        }
    }


    private static String cleanText(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("\n", " ")
                .replace("\r", " ")
                .replace("\"", "'")
                .trim();
    }
}