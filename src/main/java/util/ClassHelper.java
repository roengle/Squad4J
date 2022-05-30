package util;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassHelper {

    private ClassHelper(){
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Get a stream of all interfaces of the given class including extended interfaces and interfaces of all
     * superclasses.
     * If the given class is an interface, it will be included in the result, otherwise not.
     *
     * <a href=https://github.com/Javacord/Javacord/blob/master/javacord-core/src/main/java/org/javacord/core/util/ClassHelper.java>Javacord ClassHelper class</a>
     *
     * @param clazz The class to get the interfaces for.
     * @return The stream of interfaces of the given class.
     */
    public static Stream<Class<?>> getInterfacesAsStream(Class<?> clazz) {
        return getSuperclassesAsStream(clazz, true)
                .flatMap(superClass -> Stream.concat(
                        superClass.isInterface() ? Stream.of(superClass) : Stream.empty(),
                        Arrays.stream(superClass.getInterfaces()).flatMap(ClassHelper::getInterfacesAsStream))
                )
                .distinct();
    }

    /**
     * Get a stream of all superclasses of the given class.
     * If the given class is an interface, the result will be empty, except if {@code includeArgument} is {@code true}.
     * Whether the given class will be included in the result is controlled via the parameter {@code includeArgument}.
     *
     * <a href=https://github.com/Javacord/Javacord/blob/master/javacord-core/src/main/java/org/javacord/core/util/ClassHelper.java>Javacord ClassHelper class</a>
     *
     * @param clazz The class to get the superclasses for.
     * @param includeArgument Whether to include the given class in the result.
     * @return The stream of superclasses of the given class.
     */
    private static Stream<Class<?>> getSuperclassesAsStream(Class<?> clazz, boolean includeArgument) {
        return Stream.concat(includeArgument ? Stream.of(clazz) : Stream.empty(),
                Optional.ofNullable(clazz.getSuperclass())
                        .map(Stream::of)
                        .orElseGet(Stream::empty)
                        .flatMap(superclass -> getSuperclassesAsStream(superclass, true)));
    }

    /**
     * Get a stream of all classes that exist within a designated package, provided as a {@link String}.
     *
     * @param packageName the name of the package
     * @return a {@link Stream} representing the classes in the package
     * @throws IOException
     */
    public static Stream<Class<?>> getClassesInPackage(String packageName) throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        Set<Class<?>> classes = new HashSet<>();

        for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            if (info.getName().startsWith(packageName)) {
                final Class<?> clazz = info.load();
                // do something with your clazz
                classes.add(clazz);
            }
        }

        return classes.stream();
    }
}
