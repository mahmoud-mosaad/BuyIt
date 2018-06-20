package intelligient.transportation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.dao.SalesmanDAO;
import intelligient.transportation.models.Salesman;

@RestController
@RequestMapping("/salesman")
public class SalesmanController {

	@Autowired 
	SalesmanDAO salesmanDAO;
	
	@RequestMapping(value="/signUp", method=RequestMethod.POST)
	@ResponseBody
	public Salesman signUp(@RequestBody Salesman salesman) {
		
		salesmanDAO.createSalesman(salesman);
		return salesman;
	}
	
}
