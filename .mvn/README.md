# `.mvn/` — Configuración de Maven del proyecto

Este directorio contiene configuración compartida de Maven para **todas las máquinas**.

## ¿Por qué ya no existe `maven.config`?

La versión original traía:

    -Dmaven.repo.local=C:/Users/Usuario_Lenovo/.m2/repository

Esa es una **ruta absoluta del Windows del ingeniero**. En cualquier otro equipo:

- **Windows de otro usuario**: la carpeta `C:/Users/Usuario_Lenovo/...` no existe → falla la resolución de dependencias.
- **Linux / macOS**: Maven crea una carpeta literal `C:` dentro del proyecto (basura) y falla el build.

## Cómo configurarlo de forma portable (por máquina)

1. **Nada (recomendado)**: Maven usa por defecto `~/.m2/repository` en cualquier sistema operativo.
2. **Repositorio local distinto, por usuario** → `~/.m2/settings.xml` (no se versiona):

   ```xml
   <settings xmlns="http://maven.apache.org/SETTINGS/1.2.0">
     <localRepository>${user.home}/.m2/repository</localRepository>
   </settings>
   ```

3. **Solo una sesión**:

   ```bash
   mvn -Dmaven.repo.local=/ruta/alternativa clean package
   ```

## ¿Qué SÍ puede ir en `.mvn/maven.config`?

Solo opciones **portables** que apliquen al proyecto entero, una por línea
(Maven ≥ 3.9 admite comentarios con `#`), por ejemplo:

```
-T1C
--no-transfer-progress
```

## Referencias

- Apache Maven — Configuring: https://maven.apache.org/configure.html
- Apache Maven — Settings Reference: https://maven.apache.org/settings.html
