/**
 * 
 */
package es.cifpcm.forvagosperezb.web.bean;

import java.text.NumberFormat;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import es.cifpcm.forvagosperezb.web.model.ShoppingCart;

/**
 * @author Brian Pérez Ramos
 *
 */
@Named(value="shoppingCartBean")
@SessionScoped
public class ShoppingCartBean extends ShoppingCart{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7548244151344543067L;

	public ShoppingCartBean() {
		
	}
	
	public String getTotalAsString() {
		return NumberFormat.getCurrencyInstance().format(this.getTotal());
	}
}
