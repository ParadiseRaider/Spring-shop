package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductRepr> findById(Long id) {
        return productRepository.findById(id).map(ProductRepr::new);
    }

    public List<ProductRepr> findAllAndSplitProductsBy() {
        List<ProductRepr> result = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            result.add(new ProductRepr(product));
        }
        return result;
    }
}
