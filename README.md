# Pico Botella

Miniproyecto 1 - Sprint 1. App de juego "Pico Botella" con MVVM, Room, Navigation y Fragments.

## Funcionalidades clave
- Splash animado con salto automatico al Home.
- Home con toolbar personalizada, botella giratoria, contador y boton parpadeante.
- Retos con lista, agregar, editar y eliminar usando Room.
- Instrucciones, calificar app (Nequi) y compartir con intent del sistema.
- Dialogo de reto aleatorio con Pokemon consumido desde API publica.

## Como ejecutar
1) Abrir el proyecto en Android Studio.
2) Sincronizar Gradle.
3) Ejecutar en un dispositivo o emulador.

## Pruebas
```powershell
.\gradlew test
```

## Notas
- El audio usa tonos basicos para cumplir requisitos sin recursos externos.
- La lista de retos se inicializa con datos de ejemplo si esta vacia.
