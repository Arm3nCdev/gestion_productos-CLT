# Gestión de Productos - Aplicación de Escritorio (Java Swing + JDBC)

Aplicación de escritorio desarrollada en **Java Swing** con persistencia en **Base de Datos Relacional (JDBC)** para la gestión completa de inventario de productos (Alta, Baja, Modificación, Consulta, Ajuste de Stock y Filtros).

---


### Requisitos 
- **Java JDK 8** o superior (Probado en Java 8 / Java 11).
- **SQL Server 2022** (o MySQL / MariaDB).
- Conector JDBC incluido en la carpeta `lib/`:
  - `mssql-jdbc-12.4.2.jre8.jar` (para SQL Server).
  - `mysql-connector-j-8.0.33.jar` (para MySQL).

---

## 1. Configuración de la Base de Datos

### Opción A: SQL Server 2022 (Configuración recomendada por defecto)
1. Ejecuta el archivo `schema.sql` en tu instancia de SQL Server (vía SQL Server Management Studio o `sqlcmd`).
2. El script crea la base de datos `gestion_productos`, la tabla `productos` y el usuario `usr_productos` con la contraseña `Pass1234!`.

```bash
sqlcmd -E -i schema.sql
```

### Opción B: MySQL / MariaDB
1. Si prefieres evaluar en **MySQL**, ejecuta el script `schema_mysql.sql` en tu servidor MySQL / phpMyAdmin / MySQL Workbench.
2. Edita el archivo `db.properties` para habilitar la conexión a MySQL:

```properties
# Para MySQL desaporta las siguientes lineas en db.properties:
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/gestion_productos?useSSL=false&serverTimezone=UTC
db.user=root
db.password=tu_contraseña
```

---

##  2. Compilación y Ejecución

### En Windows (con un solo clic)
Hacer doble clic sobre el archivo **`compile_and_run.bat`** o ejecutarlo desde la terminal:

```cmd
compile_and_run.bat
```

### Desde la Terminal

**Compilar:**
```cmd
javac -encoding UTF-8 -source 1.8 -target 1.8 -cp "lib/*" -d bin src/com/gestion/modelo/*.java src/com/gestion/config/*.java src/com/gestion/dao/*.java src/com/gestion/vista/*.java src/com/gestion/main/*.java
```

**Ejecutar:**
```cmd
java -cp "bin;lib\mssql-jdbc-12.4.2.jre8.jar;lib\mysql-connector-j-8.0.33.jar" com.gestion.main.Main
```

---

##  3. Estructura del Proyecto

```
Gestio de Productos CLT/
├── src/com/gestion/
│   ├── modelo/
│   │   └── Producto.java           * Clase Entidad (atributos, getters/setters)
│   ├── config/
│   │   └── Conexion.java           * Conexión JDBC (Carga db.properties)
│   ├── dao/
│   │   └── ProductoDAO.java        * Operaciones CRUD y Consultas SQL
│   ├── vista/
│   │   └── VentanaPrincipal.java   * Interfaz gráfica Java Swing
│   └── main/
│       └── Main.java               * Punto de entrada de la aplicación
├── lib/                            * Conectores JDBC (.jar)
├── schema.sql                      * Script de base de datos para SQL Server 2022
├── schema_mysql.sql                * Script de base de datos para MySQL
├── db.properties                   * Parámetros de conexión a la BD
├── compile_and_run.bat             * Script de automatización para Windows
└── README.md                       * Documentación del proyecto


##  4. Casos de Uso y Funcionalidades Implementadas

| **1. Registrar Producto** | Ingreso de Código, Nombre, Categoría, Precio, Stock y Estado. Valida campos obligatorios y duplicidad de código. |
| **2. Listar Productos** | Visualización inmediata en tabla `JTable` de todos los registros al abrir o modificar datos. |
| **3. Consultar / Buscar** | Campo de búsqueda en tiempo real filtrando por código o por nombre. |
| **4. Seleccionar Producto** | Al hacer clic en una fila de la tabla, los datos se transfieren al formulario para edición o eliminación. |
| **5. Modificar Producto** | Permite editar los datos de un producto seleccionado manteniendo la validación de código único. |
| **6. Eliminar Producto** | Diálogo de confirmación previo (`JOptionPane`) antes de borrar el registro. |
| **7. Productos Bajo Stock** | Botón **"Bajo Stock (< 5)"** para filtrar productos con existencias críticas. |
| **8. Ajustar Stock** | Botones rápidos `+1 Unid.`, `-1 Unid.` y **"Ajustar Cantidad..."** para modificar el stock sin riesgo de valores negativos. |


## 5. Reglas de Negocio Enforzadas
- Campos obligatorios: Código y Nombre.
- Validación de Precio: Debe ser estrictamente mayor a cero (`precio > 0`).
- Validación de Stock: No se permiten valores negativos (`stock >= 0`).
- Código único: Impide guardar o modificar productos con códigos existentes en la base de datos.
