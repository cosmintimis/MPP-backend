//package org.example.serverapp.mapper;
//
//import org.example.serverapp.dto.ProductDto;
//import org.example.serverapp.entity.Product;
//
//
//public class ProductMapper {
//public static ProductDto mapToProductDto(Product product) {
//        return new ProductDto(
//                product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrice(),
//                UserMapper.mapToUserDto(product.getUser()))
//                ;
//    }
//
//    public static Product mapToProduct(ProductDto productDto) {
//        return new Product(
//                productDto.getId(),
//                productDto.getName(),
//                productDto.getDescription(),
//                productDto.getPrice(),
//                UserMapper.mapToUser(productDto.getUser())
//
//        );
//    }
//}
