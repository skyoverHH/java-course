package HomeWork5.service;

import HomeWork5.dto.ProductDto;
import HomeWork5.model.ProductEntity;
import HomeWork5.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getByAccountNumber(String accountNumber) {
        return productRepository.findByAccountNumber(accountNumber)
                .map(this::productToDto)
                .orElseThrow(() -> new NoSuchElementException("Не найдена запись Product c accountNumber = " + accountNumber));
    }

    public List<ProductDto> getAllByUserId(Long id) {
        return productRepository.findAllByUserId(id)
                .stream()
                .map(this::productToDto)
                .toList();
    }

    private ProductDto productToDto(ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getAccountNumber(), productEntity.getBalance(), productEntity.getType());
    }
}