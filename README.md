# Product Service

## Descripción

Martinez Martinez Geovani
Flores Linares Oscar Daniel
Ortiz Menez Victor Gael

Microservicio de gestión de productos y categorías, cumpliendo con los requerimientos de catálogo y almacenamiento de imágenes del proyecto.

Martinez Martinez Geovani  
Oritiz Menez Victor Gael  
Flores Linares Oscar Daniel

## Funcionalidades

| Funcionalidad | Método | Endpoint | Detalle |
|--------------|--------|----------|---------|
| **Categorías** |
| Listar todas | `GET` | `/category` | Lista todas las categorías del sistema |
| Listar activas | `GET` | `/category/active` | Solo categorías con status = 1 |
| Crear | `POST` | `/category` | Requiere rol ADMIN. Valida duplicados |
| Actualizar | `PUT` | `/category/{id}` | Requiere rol ADMIN |
| Activar/Desactivar | `PATCH` | `/category/{id}/enable|disable` | Requiere rol ADMIN |
| **Productos** |
| Listar todos | `GET` | `/product` | Lista productos con información básica |
| Obtener detalle | `GET` | `/product/{id}` | Incluye nombre de categoría |
| Crear | `POST` | `/product` | Requiere rol ADMIN. Valida categoría existente |
| Actualizar | `PUT` | `/product/{id}` | Requiere rol ADMIN. Mantiene status original |
| Actualizar stock | `PUT` | `/product/{id}/stock/{stock}` | Permite ADMIN y CUSTOMER. Usado por facturación |
| Activar/Desactivar | `PATCH` | `/product/{id}/enable|disable` | Requiere rol ADMIN |
| **Imágenes** |
| Listar imágenes | `GET` | `/product/{id}/image` | Devuelve imágenes en Base64 |
| Agregar imagen | `POST` | `/product/{id}/image` | Requiere rol ADMIN. Guarda en filesystem |
| Eliminar imagen | `DELETE` | `/product/{id}/image/{imageId}` | Requiere rol ADMIN |

## Configuración

| Componente | Valor / Detalle |
|-----------|-----------------|
| Puerto | `8080` |
| Base de Datos | MySQL (Tablas `category`, `product`, `product_image`) |
| Auth | Spring Security con JWT. Roles: ADMIN y CUSTOMER |
| Config Server | `http://localhost:8888` |
| Eureka Server | `http://localhost:8761` |
| Almacenamiento | Directorio `uploads/` para imágenes |

## Uso

1. **Dependencias**: MySQL Connector, JWT (v0.11.5), MapStruct (v1.5.5) en el `pom.xml`
2. **Base de Datos**: Ejecutar script `script/BD_Syron_P3.sql`
3. **Configuración**: Verificar credenciales MySQL en `application.properties`
4. **Ejecución**: Ejecutar `ProductApplication.java`

## Prueba de Autenticación

Los endpoints protegidos requieren el header de autorización con token JWT válido:
```
Authorization: Bearer <TU_TOKEN_JWT>
```

## Documentación

Swagger UI disponible en: `http://localhost:8080/swagger-ui.html`

Colección de Postman incluida en `json/Back.postman_collection.json` con pruebas para Product, Auth, Carrito, Invoice e imágenes.
