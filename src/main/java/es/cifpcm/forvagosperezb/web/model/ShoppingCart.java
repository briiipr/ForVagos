/**
 * 
 */
package es.cifpcm.forvagosperezb.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Pérez Ramos
 *
 */
public class ShoppingCart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -611431654609552054L;

	private final List<HotelOffer> ofertas = new ArrayList<>();
	
	public ShoppingCart() {
		
	}
	
	public void addOffer(HotelOffer ofertaAgregar, Integer nNoches) {
		ofertaAgregar.setTotal(ofertaAgregar.getPrice().multiply(new BigDecimal(nNoches)));
		this.ofertas.add(ofertaAgregar);
	}
	
	public void removeOffer(HotelOffer ofertaBorrar) {
		this.ofertas.remove(ofertaBorrar);
	}
	
	public BigDecimal getTotal() {
		return this.ofertas.stream().map(oferta -> oferta.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<HotelOffer> getOfertas() {
		return ofertas;
	}
}
