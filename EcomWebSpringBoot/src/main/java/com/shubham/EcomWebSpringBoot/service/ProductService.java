package com.shubham.EcomWebSpringBoot.service;

import java.io.IOException;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.shubham.EcomWebSpringBoot.model.Product;
import com.shubham.EcomWebSpringBoot.repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;


    public List<Product> getAllproduct() {
    	
        List<Product> products = repo.findAll(); 
        return products;
    }

  
	public Product getProductById(@PathVariable int id) {
		return repo.findById(id).get();
		
	}


	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		System.out.println("Addign in serice class");
		product.setImageName(imageFile.getOriginalFilename());
		System.out.println("Addign in serice class1");
		product.setImageType(imageFile.getContentType());
		System.out.println("Addign in serice class3");
		product.setImageDate(imageFile.getBytes()); // ✅ MATCHES the field name
		System.out.println("Addign in serice class end");
		return repo.save(product);
	}


	public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
		// TODO Auto-generated method stub
		product.setImageDate(imageFile.getBytes());
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
				
		return repo.save(product);
		
	}


	public void deleteproduct(int id) {
	repo.deleteById(id);
		
	}


	public List<Product> searchProducts(String keyword) {
		return repo.searchProducts(keyword);
	}
}

