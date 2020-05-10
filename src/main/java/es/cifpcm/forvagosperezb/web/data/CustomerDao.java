/**
 * 
 */
package es.cifpcm.forvagosperezb.web.data;

import java.util.List;

import es.cifpcm.forvagosperezb.web.model.Customer;

/**
 * @author Brian Pérez Ramos
 *
 */
public interface CustomerDao {
	public List<Customer> getAll(Integer customerId, Integer userId);
	public Customer getCustomer(String userName, String passWord);
	public Boolean deleteCustomer(Customer hotelDelete);
	public Boolean addCustomer(Customer hotelAdd);
	public Boolean editCustomer(Customer hotelEdit);
}
