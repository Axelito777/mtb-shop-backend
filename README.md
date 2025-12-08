# MTB Shop Backend API

Backend REST API para tienda de componentes MTB (Mountain Bike) desarrollado con Spring Boot 3.2 y PostgreSQL (Supabase).

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security + JWT**
- **Spring Data JPA**
- **PostgreSQL** (Supabase)
- **Maven**

## 📋 Prerequisitos

- Java 17 o superior
- Maven 3.6+ (o usar el wrapper incluido)
- PostgreSQL (o cuenta en Supabase)

## ⚙️ Configuración

1. Clona el repositorio
2. Configura las variables de entorno en `application.properties`:
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`
   - `application.security.jwt.secret-key`

## 🏃 Ejecución

### Con Maven Wrapper (recomendado):

**Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

### Con Maven instalado:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080/api`

## 📡 Endpoints API

### 🔐 Autenticación (`/api/auth`)

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| POST | `/auth/register` | Registrar nuevo usuario | No |
| POST | `/auth/login` | Iniciar sesión | No |

### 👤 Usuarios (`/api/users`)

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| GET | `/users` | Listar todos los usuarios | Admin |
| GET | `/users/{id}` | Obtener usuario por ID | Sí |
| GET | `/users/email/{email}` | Obtener usuario por email | Sí |
| PUT | `/users/{id}` | Actualizar usuario | Sí |
| DELETE | `/users/{id}` | Eliminar usuario | Admin |

### 📦 Productos (`/api/products`)

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| GET | `/products` | Listar todos los productos | No |
| GET | `/products/available` | Productos disponibles (stock > 0) | No |
| GET | `/products/category/{categoryId}` | Productos por categoría | No |
| GET | `/products/{id}` | Obtener producto por ID | No |
| POST | `/products` | Crear producto | Admin |
| PUT | `/products/{id}` | Actualizar producto | Admin |
| DELETE | `/products/{id}` | Eliminar producto | Admin |

### 📂 Categorías (`/api/categories`)

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| GET | `/categories` | Listar todas las categorías | No |
| GET | `/categories/{id}` | Obtener categoría por ID | No |
| POST | `/categories` | Crear categoría | Admin |
| PUT | `/categories/{id}` | Actualizar categoría | Admin |
| DELETE | `/categories/{id}` | Eliminar categoría | Admin |

### 🛒 Órdenes (`/api/orders`)

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| GET | `/orders` | Listar todas las órdenes | Admin |
| GET | `/orders/my-orders` | Mis órdenes | Sí |
| GET | `/orders/user/{userId}` | Órdenes de un usuario | Admin |
| GET | `/orders/{id}` | Obtener orden por ID | Sí |
| POST | `/orders` | Crear orden | Sí |
| PATCH | `/orders/{id}/status` | Actualizar estado de orden | Admin |

**Total: 27 endpoints**

## 📝 Ejemplos de Request

### Registro de Usuario
```json
POST /api/auth/register
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+56912345678",
  "address": "Calle Ejemplo 123"
}
```

### Login
```json
POST /api/auth/login
{
  "email": "user@example.com",
  "password": "password123"
}
```

### Crear Producto
```json
POST /api/products
Authorization: Bearer {token}
{
  "name": "Shimano XT M8100",
  "description": "Cambio trasero 12 velocidades",
  "price": 89990,
  "stock": 15,
  "imageUrl": "https://example.com/image.jpg",
  "categoryId": 1,
  "brand": "Shimano",
  "model": "XT M8100"
}
```

### Crear Orden
```json
POST /api/orders
Authorization: Bearer {token}
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ],
  "shippingAddress": "Calle Ejemplo 123, Santiago",
  "paymentMethod": "Credit Card"
}
```

## 🔒 Autenticación

La API usa JWT (JSON Web Tokens) para autenticación.

1. Obtén un token usando `/api/auth/login` o `/api/auth/register`
2. Incluye el token en el header de tus requests:
   ```
   Authorization: Bearer {tu-token-aquí}
   ```

### Roles:
- **USER**: Usuario regular (puede ver productos, crear órdenes, ver su perfil)
- **ADMIN**: Administrador (puede crear/editar/eliminar productos, categorías, ver todos los usuarios y órdenes)

## 🗄️ Modelo de Datos

### User
- id, email, password, firstName, lastName, phone, address, role, createdAt, updatedAt

### Product
- id, name, description, price, stock, imageUrl, categoryId, brand, model, createdAt, updatedAt

### Category
- id, name, description, imageUrl, createdAt

### Order
- id, userId, total, status, shippingAddress, paymentMethod, createdAt, updatedAt

### OrderItem
- id, orderId, productId, quantity, price, subtotal

## 🛠️ Arquitectura

```
src/main/java/com/mtbshop/
├── config/          # Configuraciones (Security, CORS)
├── controller/      # Controladores REST
├── dto/            # Data Transfer Objects
│   ├── request/    # DTOs de entrada
│   └── response/   # DTOs de salida
├── model/          # Entidades JPA
├── repository/     # Repositorios Spring Data
├── service/        # Lógica de negocio
├── security/       # JWT Filter, UserDetailsService
└── exception/      # Manejo global de excepciones
```

## 📊 Estados de Orden

- `PENDING`: Orden creada, pendiente de pago
- `PROCESSING`: Orden en proceso
- `SHIPPED`: Orden enviada
- `DELIVERED`: Orden entregada
- `CANCELLED`: Orden cancelada

## 🧪 Testing

Para probar los endpoints, puedes usar:
- **Postman**
- **Thunder Client** (VS Code extension)
- **cURL**

## 📄 Licencia

Este proyecto fue desarrollado para fines educativos.

## 👨‍💻 Autor

Desarrollado como parte de la evaluación de Fullstack.
