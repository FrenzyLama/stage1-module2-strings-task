package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String regexForSplitParameterPart = ("^(.*?)\\(");
        Pattern pattern = Pattern.compile(regexForSplitParameterPart);
        Matcher matcher = pattern.matcher(signatureString);
        int index;

        // Separate signature string
        List<String> accessModReturnTypeMethodName = new ArrayList<>();
        List<MethodSignature.Argument> arguments = new ArrayList<>();
        if (matcher.find()) {
            String match = matcher.group(1);
            accessModReturnTypeMethodName = List.of(match.split(" "));
            index = matcher.end(1);

            String argumentsString = signatureString.substring(index);
            arguments = getArgumentsFromSignature(argumentsString);
        }

        // Access modifier, return type, method name
        String accessModifier = "";
        String returnType;
        String methodName;
        if (accessModReturnTypeMethodName.size() == 3) {
            accessModifier = accessModReturnTypeMethodName.get(0);
            returnType = accessModReturnTypeMethodName.get(1);
            methodName = accessModReturnTypeMethodName.get(2);
        } else {
            returnType = accessModReturnTypeMethodName.get(0);
            methodName = accessModReturnTypeMethodName.get(1);
        }

        // Initialize MethodSignature object
        MethodSignature methodSignature = new MethodSignature(methodName, arguments);

        // Set values to MethodSignature object
        if (!Objects.equals(accessModifier, "")) {
            methodSignature.setAccessModifier(accessModifier);
        }
        methodSignature.setReturnType(returnType);

        return methodSignature;
    }

    public static void main(String[] args) {
        String method = "accessModifier returnType methodName()";
        MethodSignature methodSignature = new MethodParser().parseFunction(method);

        System.out.println(methodSignature);
    }

    private List<MethodSignature.Argument> getArgumentsFromSignature(String signature) {
        List<MethodSignature.Argument> argumentList = new ArrayList<>();
        String[] arguments = signature.split(",");

        if (!Objects.equals(arguments[0], "()")) {
            for (String argument : arguments) {
                String argumentWithTrim = argument.trim().replaceAll("[()]", "");
                String[] typeAndNameOfArgument = argumentWithTrim.split(" ");
                MethodSignature.Argument arg = new MethodSignature.Argument(typeAndNameOfArgument[0], typeAndNameOfArgument[1]);
                argumentList.add(arg);
            }
        }

        return argumentList;
    }
}
