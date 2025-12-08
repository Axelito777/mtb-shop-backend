package com.mtbshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private UserResponse user;
    private List<OrderItemResponse> items;
    private BigDecimal total;
    private String status;
    private String shippingAddress;
    private String paymentMethod;
    private LocalDateTime createdAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponse {
        private Long id;
        private ProductResponse product;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;
    }
}
