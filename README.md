# Rumy
A simple library that simplifies java reflection.

# Add Rumy to your project
```groovy
repositories {
    maven {
      url 'https://jitpack.io'
    }
}
```
```groovy
dependencies {
    implementation group: 'com.github.Hippo', name: 'Rumy', version: '1.0.0'
}
```

# How to use
Getting static access to a class:
```java
ClassContext classContext = ClassLookup.lookup("MyClass");
```

Allocating Objects:

```java
import static rip.hippo.rumy.allocate.ObjectAllocator.*;
ClassContext classContext = allocate("MyClass");
```

Parameter Pattern Matching:

By default, there is 3 possible options to match parameter types (for constructors and methods), empty, strong, and weak.

If no parameter pattern matcher is set it will default to empty.

Empty Pattern Matching:

As the name suggest, this will match methods and constructors with no parameters.

Strong Pattern Matching:

Strong pattern matching makes you specify the class types and the parameter values, this is to avoid mismatching with primitive and primitive wrappers.

```java
import static rip.hippo.rumy.allocate.ObjectAllocator.*;
ClassContext classContext = ...;
classContext.invoke("myMethod",ofStrong(int.class).then(420));
```

Weak pattern matching:

Weak pattern matching only makes you specify the parameter values.

```java
import static rip.hippo.rumy.allocate.ObjectAllocator.*;
ClassContext classContext = ...;
classContext.invoke("betterMethod",ofWeak("Cool value"));
```

The examples above shows you how to invoke methods with the parameter pattern matching, the same concept applies to allocating objects.

```java
import static rip.hippo.rumy.allocate.ObjectAllocator.*;
ClassContext classContext = allocate("MyClass",ofWeak("Another cool value"));
```