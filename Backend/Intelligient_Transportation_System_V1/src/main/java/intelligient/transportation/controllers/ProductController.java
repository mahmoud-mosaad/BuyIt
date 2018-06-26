package intelligient.transportation.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import intelligient.transportation.dao.ProductDAO;
import intelligient.transportation.dao.TokenHandler;
import intelligient.transportation.dao.UserDAO;
import intelligient.transportation.models.Customer;
import intelligient.transportation.models.Product;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductDAO productRepository;
	
	@RequestMapping(value="/addProduct", method=RequestMethod.POST)
	@ResponseBody
	public Boolean addProduct(@RequestParam("file") MultipartFile file, 
			@RequestParam("product") String productJSON, @RequestHeader String token, 
			@RequestHeader String type, HttpServletResponse response) {

		System.out.println(type);
		int decodedUserId = TokenHandler.getUserIdFromToken(token);
		if(decodedUserId==-1 || !type.equals("Admin")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
		
		
		
		if (!file.isEmpty()) {
            try {
        		Product prod = new ObjectMapper().readValue(productJSON, Product.class);
				prod.setPhoto("ProductsImages/" + 
          				prod.getName() + "_" + file.getOriginalFilename());
        		
        		if(prod.getName().trim().equals("") || prod.getPrice()==null)
        	    	prod.setName(null);
        		
            	Boolean save = productRepository.addProduct(prod);
            	if(save==false){
            		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            		return false;
            	}
            	
                byte[] bytes = file.getBytes();                
                String path = "src/main/resources/static/ProductsImages/" + 
                				prod.getName() + "_" + file.getOriginalFilename();
        	    
				try {
					 BufferedOutputStream stream = 
		                        new BufferedOutputStream(new FileOutputStream
		                        		(new File(path)));
					

		             stream.write(bytes);
		             stream.close();
		            } catch (Exception e) {
		            	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		                System.out.println( "You failed to covert " + prod.getName() + 
		                		" " + file.getName() + " => " + e.getMessage());
		                return false;
		            }	

                
                return  true;
            } catch (Exception e) {
            	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            	System.out.println("You failed to add the product because " + e);
                return false;
            }
        } else {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	System.out.println("You failed to add the product because the file was empty.");
            return false;
        }
	       
	}
	
/*
	 @RequestMapping(method=RequestMethod.POST, path= "/createProduct")
	    public void  createProduct(@Valid @RequestBody Product product) {
	         productRepository.createProduct(product);
	   
	         
	    }
	*/ 
	   @RequestMapping(method=RequestMethod.GET, path= "/getProduct/{productId}")
	    public Product getProductById(@PathVariable int productId, 
	    		@RequestHeader String token, HttpServletResponse response) {
		   
		    //System.out.println(token);
			int decodedUserId = TokenHandler.getUserIdFromToken(token);
			//System.out.println(decodedUserId);
			if(decodedUserId==-1) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}   
			
	        return productRepository.getById(productId);
	     
	    }
	  
	    
	    @RequestMapping(method=RequestMethod.GET, path= "/getAllProducts")
	    public List getAllProducts() {
	    	
	        return productRepository.getAllProduct();
	    }
	    

	
}
