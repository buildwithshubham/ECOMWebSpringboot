package com.shubham.EcomWebSpringBoot.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shubham.EcomWebSpringBoot.model.Product;
import com.shubham.EcomWebSpringBoot.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	
	//@RequestMapping("/products")
	//public List<Product> getAllProducts(){
	//	return service.getAllproduct();}
	
	@RequestMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<>(service.getAllproduct(), HttpStatus.OK);}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		
		Product product = service.getProductById(id);
		if (product != null)
		return new ResponseEntity<>(product, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	
	
	@PostMapping("/product")   
	public ResponseEntity<?> addProduct(@RequestPart Product product , @RequestPart MultipartFile imageFile){
		try{
			System.out.println("Addign prduct");
			Product product1 = service.addProduct(product,imageFile);
			System.out.println("Addign prductfinal");
			
			return new ResponseEntity<>(product1,HttpStatus.CREATED);
		}
		catch(Exception e){
			System.out.println("falure while adding product");
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		}
	@GetMapping("product/{id}/image")   
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id){
		System.out.println("to get imahe starting");
		Product product = service.getProductById(id);
		byte[] imageFile =product.getImageDate();
		System.out.println("to get imahe edding");
		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
		}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
		Product product1= null;
		try {
			product1=service.updateProduct(id,product,imageFile);
			
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
		if(product1 != null)
			return new ResponseEntity<>("Updated", HttpStatus.OK); 
		else
			return new ResponseEntity<>("Failto update", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		Product product = service.getProductById(id);
		if(product != null)
		{
			service.deleteproduct(id);
			return new ResponseEntity<>("deleted", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Product not found", HttpStatus.OK);
		}
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
		System.out.println("searching with" + keyword);
		List<Product> products = service.searchProducts(keyword);
		return new ResponseEntity<>(products, HttpStatus.OK);
		
	}
	
}
	
