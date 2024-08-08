package controller;

import model.game.enemies.Enemy;
import model.game.enemies.smiley.Fist;
import model.game.enemies.smiley.Hand;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassFinder {
    public static <T> ArrayList<Class> getSubClasses(Class<T> tClass, String packagePath){
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(packagePath.replaceAll(
                "[.]", "/"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        Predicate<String> predicate = (Predicate<String>) s -> {
            boolean result = s.endsWith(".class");
            if (result){
                Class clazz = getClass(s, packagePath);
                return (clazz.getSuperclass().equals(tClass) && !clazz.equals(Fist.class) && !clazz.equals(Hand.class));
            }
            return false;
        };
        Function<String, Class> function = s -> getClass(s, packagePath);
        Set<Class> classes = bufferedReader.lines().filter(predicate).map(function).collect(Collectors.toSet());
        return new ArrayList<>(classes);
    }
    private static Class getClass(String className, String packageName){
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
