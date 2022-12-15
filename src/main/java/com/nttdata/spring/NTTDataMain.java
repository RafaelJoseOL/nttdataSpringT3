package com.nttdata.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nttdata.spring.persistence.Customer;
import com.nttdata.spring.services.CustomerManagementI;

/**
 * Taller Spring - 3
 * 
 * Clase principal
 * 
 * @author Rafael José
 *
 */
@SpringBootApplication
public class NTTDataMain implements CommandLineRunner {

	/** Servicio Cliente */
	@Autowired
	private CustomerManagementI customerService;

	private static final Logger LOG = LoggerFactory.getLogger(NTTDataMain.class);

	/**
	 * 
	 * Metodo principal.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(NTTDataMain.class, args);
	}

	/**
	 * Metodo para gestionar inserciones y búsquedas en la BBDD.
	 */
	public void run(String... args) {

		// Constantes
		final String CUSTOMERID = "Customer ID: ";
		final String RAFAELJOSE = "Rafael Jose";
		final String EDUARDO = "Eduardo";
		final String PEREZMARTINEZ = "Perez Martinez";

		// Creación de clientes
		Customer customer1 = new Customer();
		Customer customer2 = new Customer();
		Customer customer3 = new Customer();
		Customer customer4 = new Customer();
		Customer customer5 = new Customer();
		customerService.createCustomer(customer1, RAFAELJOSE, "Ossorio Lopez", "1994-06-09", "11223344A");
		customerService.createCustomer(customer2, RAFAELJOSE, "Osorio Lopez", "2001-01-15", "22558877E");
		customerService.createCustomer(customer3, EDUARDO, PEREZMARTINEZ, "1998-07-18", "35789514D");
		customerService.createCustomer(customer4, "Pedro", "Soria Nuñez", "1978-12-26", "95784531F");
		customerService.createCustomer(customer5, EDUARDO, PEREZMARTINEZ, "1991-10-01", "44532783N");

		// Inserción de clientes en la BBDD.
		customerService.addCustomer(customer1);
		customerService.addCustomer(customer2);
		customerService.addCustomer(customer3);
		customerService.addCustomer(customer4);
		customerService.addCustomer(customer5);

		// Búsquedas en la BBDD y asignación a las listas.
		List<Customer> allCustomers = customerService.findAll();
		List<Customer> customersRJ = customerService.findByCustomerName(RAFAELJOSE);
		List<Customer> customersRJOL = customerService.findByCustomerNameAndCustomerLastName(RAFAELJOSE,
				"Ossorio Lopez");
		List<Customer> customersEPM = customerService.findByCustomerNameAndCustomerLastName(EDUARDO, PEREZMARTINEZ);
		List<Customer> customersEPMDoc = customerService.findByCustomerNameAndCustomerLastNameAndDocument(EDUARDO,
				PEREZMARTINEZ, "35789514D");		

		// Resultados de las búsquedas mediante logger.
		LOG.info("Customer: ");
		for (int i = 0; i < allCustomers.size(); i++) {
			LOG.info("{}{} {}", CUSTOMERID, allCustomers.get(i).getCustomerName(),
					allCustomers.get(i).getCustomerLastName());
		}
		LOG.info("Customers named {}: ", RAFAELJOSE);
		for (int i = 0; i < customersRJ.size(); i++) {
			LOG.info("{}{}", CUSTOMERID, customersRJ.get(i).getCustomerId());
		}
		LOG.info("Customers named {} Ossorio Lopez: ", RAFAELJOSE);
		for (int i = 0; i < customersRJOL.size(); i++) {
			LOG.info("{}{}", CUSTOMERID, customersRJOL.get(i).getCustomerId());
		}
		LOG.info("Customers named {} {}: ", EDUARDO, PEREZMARTINEZ);
		for (int i = 0; i < customersEPM.size(); i++) {
			LOG.info("{}{}", CUSTOMERID, customersEPM.get(i).getCustomerId());
		}
		LOG.info("Customers named {} {} with 35789514D as it's document: ", EDUARDO, PEREZMARTINEZ);
		for (int i = 0; i < customersEPMDoc.size(); i++) {
			LOG.info("{}{}", CUSTOMERID, customersEPMDoc.get(i).getCustomerId());
		}
		
		// Borrado de cliente y nueva consulta
		customerService.deleteCustomer(customer5);
		allCustomers = customerService.findAll();
		LOG.info("Customer: ");
		for (int i = 0; i < allCustomers.size(); i++) {
			LOG.info("{}{} {}", CUSTOMERID, allCustomers.get(i).getCustomerName(),
					allCustomers.get(i).getCustomerLastName());
		}
	}
}
