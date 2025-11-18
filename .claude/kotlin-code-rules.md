# Kotlin Code Rules - AquaMate

## Language Requirement

**ALL CODE MUST BE WRITTEN IN ENGLISH.**

This includes:
- Variable, function, class, property, parameter, and package names
- Comments (if absolutely necessary)
- Documentation (KDoc)
- String constants
- Enum values
- Test names

## Code Documentation

### No Inline Comments

**DO NOT add `//` type comments within the code.**

Code should be self-documenting. Use descriptive names for variables, functions, and classes instead of adding comments.

### KDoc Documentation

**Only add KDoc to public elements (public API).**

Internal, private, and protected elements do not require KDoc documentation.

Elements that require KDoc:
- Public classes, interfaces, functions, and properties
- Public enums and their values when not obvious

Elements that DO NOT require KDoc:
- Private/internal/protected functions and properties
- Local variables
- Parameters of private functions

## Code Generation Rules

### Strictly Requested Code Only

**Only generate explicitly requested code. Do not add additional unrequested code.**

When asked to create a specific function, class, or file:
- Create only what was requested
- Do not add unrequested helper functions
- Do not add unnecessary imports
- Do not add TODOs or comments about future improvements

Exceptions:
1. Necessary imports for compilation
2. Critical error handling if a function can obviously fail

## File Format

**All Kotlin files must end with exactly ONE blank line.**

Important:
- If the file already has one blank line at the end, do NOT add another one
- There should be exactly one empty line after the last line of code
- Never have multiple blank lines at the end of a file
- Never have zero blank lines at the end of a file

Example of correct format:
```kotlin
package com.example

class Example {
    fun doSomething() {
        println("Hello")
    }
}
⏎
```

The `⏎` represents exactly ONE blank line at the end.

## Single Responsibility Principle

**Each class/function should have one clear responsibility.**

If a class has multiple concerns:
- Extract helper functions to extension functions
- Create dedicated handler/utility classes
- Separate error handling into dedicated classes

Example:
- Error mapping should be in dedicated `ErrorHandler` classes
- Data transformations should be in `Mapper` classes
- Validation logic should be in `Validator` classes

## Code Organization

### Order of Elements in a Class

1. Companion object (if exists)
2. Properties
3. Init block (if exists)
4. Secondary constructor (if exists)
5. Public functions
6. Private functions

### Imports

- Group imports by package
- Do not use wildcard imports (*)
- Sort alphabetically
- Separate platform imports from third-party library imports

## Naming

### Descriptive Names

Variable, function, and class names must be descriptive enough to eliminate the need for comments.

### Naming Conventions

- Classes and Interfaces: PascalCase
- Functions and variables: camelCase
- Constants: UPPER_SNAKE_CASE
- Packages: lowercase without underscores
- Files: PascalCase corresponding to the main class

### Boolean Expressions

Boolean variables and functions should be named as questions:

```kotlin
val isInitialized: Boolean
val hasNetworkConnection: Boolean
fun isValid(): Boolean
fun canExecute(): Boolean
```

Avoid negative boolean names to prevent double negatives.

## Line Length

**Maximum 120 characters per line.**

## Functions

- A function should do one thing only
- Ideally, a function should not exceed 20 lines
- Maximum 5 parameters per function
- If you need more parameters, create a data class to group them

## Error Handling

### Validations

Use `require` to validate input parameters:

```kotlin
fun calculateHydration(weightKg: Double): Int {
    require(weightKg > 0) { "Weight must be greater than 0" }
    return (weightKg * 30).toInt()
}
```

Use `check` to validate internal state.

### Result Type

Prefer `Result<T>` over exceptions for operations that can fail.

## Immutability

- Prefer `val` over `var`
- Use immutable collections by default
- Only use mutable collections when modification is necessary

## Null Safety

- Do not use nullable types when not necessary
- Use the Elvis operator for default values
- Avoid `!!` (not-null assertion)

## Kotlin Multiplatform Specific

### Expect/Actual

Document `expect` functions with KDoc as they are part of the public contract.
`actual` implementations do not need to repeat KDoc.

### commonMain

Code in `commonMain` must be pure Kotlin without platform-specific dependencies.

### Platform-Specific Code

Minimize platform-specific code. Only use when absolutely necessary.

## Data Classes

Keep data classes simple and focused. Do not add logic to data classes.

## Extension Functions

Extension functions should be in separate files named appropriately (e.g., `DoubleExtensions.kt`).

## Constants

Group related constants in objects.

## Testing

### Test Naming

Test function names should clearly describe what is being tested using backticks.

### Test Structure

Follow the AAA pattern (Arrange-Act-Assert).

## Interface-First Principle

**ALWAYS create an interface for implementations.**

When creating any of the following, you MUST create an interface first and then the implementation:
- Repositories (e.g., `LoginRepository` interface + `LoginRepositoryImpl` implementation)
- Data Sources (e.g., `AuthRemoteDataSource` interface + `AndroidAuthFirebaseDataSource` implementation)
- Use Cases (e.g., `SignInWithEmailUseCase` interface + `SignInWithEmailUseCaseImpl` implementation)
- Mappers (e.g., `UserMapper` interface + `UserMapperImpl` implementation)
- Handlers (e.g., `FirebaseAuthErrorHandler` interface + `FirebaseAuthErrorHandlerImpl` implementation)
- Any class that will be injected via dependency injection

**Exception: ViewModels do NOT need interfaces.**

ViewModels (classes extending `ViewModel`) should be created directly without an interface:
- Use `LoginViewModel` directly, not `LoginViewModel` interface + `LoginViewModelImpl`
- ViewModels are framework components and don't need abstraction
- Inject ViewModels as concrete classes in DI modules

### Dependency Injection Rule

**ALWAYS inject interfaces, NEVER concrete implementations.**

When configuring dependency injection (Koin, Dagger, etc.):
- Bind the interface type, not the implementation
- Dependencies should depend on abstractions, not concretions

**Correct:**
```kotlin
// Interface
interface UserMapper {
    fun mapToUser(authUser: AuthUser): User
}

// Implementation
internal class UserMapperImpl : UserMapper {
    override fun mapToUser(authUser: AuthUser): User {
        return User(id = authUser.uid, email = authUser.email)
    }
}

// DI Module
val domainModule = module {
    factory<UserMapper> { UserMapperImpl() }
}

// Usage in another class
class SomeUseCase(private val mapper: UserMapper) {
    // depends on interface, not implementation
}
```

**Incorrect:**
```kotlin
// No interface, direct implementation
class UserMapper {
    fun mapToUser(authUser: AuthUser): User {
        return User(id = authUser.uid, email = authUser.email)
    }
}

// DI Module injecting concrete class
val domainModule = module {
    factory { UserMapper() }
}
```

### Benefits

This approach ensures:
- **Dependency Inversion Principle**: High-level modules don't depend on low-level modules
- **Testability**: Easy to mock interfaces in unit tests
- **Flexibility**: Can swap implementations without changing dependent code
- **Loose Coupling**: Components are decoupled and more maintainable

## Summary of Rules

1. ✅ ALL code must be in English
2. ❌ NO `//` comments in code
3. ✅ Self-documenting code with descriptive names
4. ✅ KDoc only for public APIs
5. ❌ DO NOT add unrequested code
6. ✅ One blank line at the end of each file
7. ✅ Maximum 120 characters per line
8. ✅ Single Responsibility Principle - separate concerns
9. ✅ **Interface-First Principle - always create interfaces for implementations (except ViewModels)**
10. ✅ **Inject interfaces, never concrete implementations (except ViewModels)**
11. ✅ Prefer `val` over `var`
12. ✅ Use `require` and `check` for validations
13. ✅ Use `Result<T>` for operations that can fail
14. ❌ Avoid `!!` and unnecessary nulls
15. ✅ Boolean names as questions (isValid, hasPermission)
16. ✅ Enum values in UPPER_SNAKE_CASE
17. ✅ Keep data classes simple