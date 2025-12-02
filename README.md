# Aquamate Mobile Health - README

## 📘 Overview

Aquamate Mobile Health es una aplicación móvil desarrollada con **Kotlin Multiplatform (KMP)**, enfocada en el monitoreo y gestión de parámetros de calidad del agua. El proyecto integra un módulo compartido (shared) para la lógica central y módulos nativos para Android y iOS.

## 🚀 Tecnologías principales

* **Kotlin Multiplatform (KMP)**
* **Compose Multiplatform / Jetpack Compose** para Android
* **SwiftUI** para iOS
* **Firebase** (Auth, Firestore)
* **Ktor** para networking
* **Koin / inyección de dependencias**

## 📂 Estructura del Proyecto

```
mobile-health-aquamate-main/
├── shared/            // Lógica común KMP
├── androidApp/        // Implementación Android
├── iosApp/            // Implementación iOS
└── buildSrc/          // Configuración de build
```

### Módulo shared

Contiene:

* Servicios de datos
* Casos de uso
* Modelos
* Repositorios
* Integración con Firebase

### Android

Implementado con Jetpack Compose, navegación declarativa y consumo del shared module.

### iOS

Implementado con SwiftUI, conectando ViewModels expuestos desde KMP.

## 🔐 Integración con Firebase

El proyecto usa:

* **Firebase Auth** para autenticación
* **Firestore** para almacenar parámetros monitoreados

Archivos relevantes:

* `firebase.json`
* `firestore.rules`

## ▶️ Ejecución

### Android

```
./gradlew :androidApp:installDebug
```

### iOS

Abrir `iosApp/` en Xcode y ejecutar.

## 📄 Licencia

Este proyecto se distribuye bajo la licencia especificada en el archivo `LICENSE` (si aplica).

---

Si deseas, puedo extender o agregar secciones específicas al README según tus necesidades.
