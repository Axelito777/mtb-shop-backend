package com.mtbshop.config;

import com.mtbshop.model.Category;
import com.mtbshop.model.Product;
import com.mtbshop.model.Role;
import com.mtbshop.model.User;
import com.mtbshop.repository.CategoryRepository;
import com.mtbshop.repository.ProductRepository;
import com.mtbshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;  // ← AGREGADO
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        initializeAdmin();
        initializeCategories();
        initializeProducts();
    }
    
    private void initializeAdmin() {
        if (!userRepository.existsByEmail("superadmin@mtb.com")) {
            User admin = new User();
            admin.setEmail("superadmin@mtb.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Super");
            admin.setLastName("Admin");
            admin.setPhone("+56912345678");
            admin.setAddress("Santiago, Chile");
            admin.setRole(Role.ADMIN);
            admin.setCreatedAt(LocalDateTime.now());
            
            userRepository.save(admin);
            log.info("✅ Usuario admin creado: superadmin@mtb.com / admin123");
        } else {
            log.info("✅ Usuario admin ya existe");
        }
    }
    
    private void initializeCategories() {
        if (categoryRepository.count() == 0) {
            List<Category> categories = Arrays.asList(
                createCategory("Frenos", "Sistemas de frenado hidráulicos y mecánicos"),
                createCategory("Suspensiones", "Horquillas y amortiguadores"),
                createCategory("Transmisión", "Grupos de cambio y desviadores"),
                createCategory("Ruedas", "Ruedas y llantas para MTB")
            );
            
            categoryRepository.saveAll(categories);
            log.info("✅ {} categorías creadas", categories.size());
        } else {
            log.info("✅ Categorías ya existen");
        }
    }
    
    private void initializeProducts() {
        if (productRepository.count() == 0) {
            Category frenos = categoryRepository.findById(1L).orElse(null);
            Category suspensiones = categoryRepository.findById(2L).orElse(null);
            Category transmision = categoryRepository.findById(3L).orElse(null);
            Category ruedas = categoryRepository.findById(4L).orElse(null);
            
            List<Product> products = Arrays.asList(
                createProduct("SRAM Code RSC", "Frenos de disco hidráulicos de alto rendimiento", 
                    new BigDecimal("129990"), 8, frenos, "SRAM", "Code RSC", 
                    "https://images.unsplash.com/photo-1576435728678-68d0fbf94e91"),
                    
                createProduct("RockShox Pike Ultimate", "Horquilla de suspensión 160mm travel", 
                    new BigDecimal("899990"), 5, suspensiones, "RockShox", "Pike Ultimate", 
                    "https://images.unsplash.com/photo-1532298229144-0ec0c57515c7"),
                    
                createProduct("Shimano XT M8100", "Grupo de transmisión 12 velocidades", 
                    new BigDecimal("799990"), 6, transmision, "Shimano", "XT M8100", 
                    "https://images.unsplash.com/photo-1571333250630-f0230c320b6d"),
                    
                createProduct("DT Swiss XM 1700", "Ruedas 29 pulgadas tubeless ready", 
                    new BigDecimal("449990"), 4, ruedas, "DT Swiss", "XM 1700", 
                    "https://images.unsplash.com/photo-1485965120184-e220f721d03e")
            );
            
            productRepository.saveAll(products);
            log.info("✅ {} productos creados", products.size());
        } else {
            log.info("✅ Productos ya existen");
        }
    }
    
    private Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return category;
    }
    
    private Product createProduct(String name, String description, BigDecimal price, 
                                  Integer stock, Category category, String brand, 
                                  String model, String imageUrl) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        product.setBrand(brand);
        product.setModel(model);
        product.setImageUrl(imageUrl);
        return product;
    }
}