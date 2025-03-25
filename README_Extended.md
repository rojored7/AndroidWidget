# AndroidWidget

**AndroidWidget** es una aplicación de ejemplo desarrollada para demostrar cómo integrar **Instana**, la herramienta de observabilidad de IBM, con una aplicación móvil de Android. El objetivo principal de este proyecto es analizar cómo se puede realizar el monitoreo en tiempo real de una app Android a través de eventos personalizados y rendimiento de widgets utilizando Instana.

---

## 🎯 Propósito del Proyecto

Este proyecto fue diseñado con fines educativos y de prueba para explorar las capacidades de **Instana** dentro del contexto de una aplicación móvil Android. La app implementa un **widget interactivo** que se actualiza y comunica con el sistema, generando eventos que se capturan y se envían a Instana para observación.

---

## 🚀 Funcionalidades Clave

### 🧩 Integración con Instana

- Uso del agente móvil de Instana (Android Agent).
- Seguimiento de eventos personalizados (custom events) disparados desde la aplicación y el widget.
- Observación del rendimiento del widget y sus interacciones con el sistema Android.
- Captura de logs e información de latencia.
- Configuración del reporting automático hacia un backend de Instana.

### 📱 Implementación del Widget

- Creación de un widget interactivo que puede ser agregado a la pantalla de inicio.
- El widget responde a eventos como clics o actualizaciones automáticas.
- Genera eventos que se pueden rastrear en Instana.

### 📡 Comunicación con Backend (opcional)

- Aunque el enfoque está en el cliente Android, se puede integrar fácilmente con un backend que reciba y confirme eventos observables para pruebas de rendimiento y trazabilidad.

---

## 🛠️ Tecnologías Utilizadas

- **Kotlin**: Lenguaje principal del proyecto.
- **Instana Android SDK**: Para la instrumentación y observabilidad.
- **Android Jetpack (AppWidgetProvider)**: Para la creación del widget.
- **JetBrains Android Tools / Android Studio**: Entorno de desarrollo.

---

## 📦 Estructura del Proyecto

```
AndroidWidget/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/androidwidget/
│   │       │   ├── WidgetProvider.kt        # Lógica principal del widget
│   │       │   ├── InstanaEvents.kt         # Envío de eventos a Instana
│   │       └── res/layout/
│   │           └── widget_layout.xml        # Vista del widget
│   ├── AndroidManifest.xml
├── build.gradle
└── README.md
```

---

## ⚙️ Cómo Ejecutar

1. **Clonar el repositorio**

```bash
git clone https://github.com/rojored7/AndroidWidget.git
```

2. **Abrir en Android Studio**

- Selecciona `Open an Existing Project`
- Dirígete a la carpeta clonada

3. **Agregar clave Instana (si aplica)**

- Crea un archivo `local.properties` con:

```
INSTANA_KEY=tu_clave_de_instana
```

O configura directamente desde el código si estás usando `Instana.setup()`.

4. **Ejecutar en emulador o dispositivo**

- Ejecuta el proyecto con un dispositivo o AVD habilitado.

---

## 📊 ¿Qué puedes observar en Instana?

- **Eventos personalizados** generados por el usuario desde el widget.
- **Rendimiento del inicio de la aplicación.**
- **Errores y excepciones** lanzadas en tiempo de ejecución.
- **Tiempo de respuesta** al interactuar con el widget.
- **Ciclos de actualización automática del widget.**

---

## 🤝 Contribuciones

Si deseas contribuir:

1. Haz un fork
2. Crea una rama: `feature/tu-mejora`
3. Haz commit y push
4. Crea un Pull Request

---

## 📄 Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

## 👤 Autor

**rojored7**  
GitHub: [https://github.com/rojored7](https://github.com/rojored7)

