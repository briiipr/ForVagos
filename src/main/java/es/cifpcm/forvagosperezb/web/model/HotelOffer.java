/**
 * 
 */
package es.cifpcm.forvagosperezb.web.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Brian Pérez Ramos
 *
 */
public class HotelOffer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3591895037969060087L;
	
	private Integer hotelId;
	private String name;
	private BigDecimal price;
	private BigDecimal total;
	private String hotelPicture;
	private Integer idMunicipio;
	private Integer idProvincia;
	
	public HotelOffer() {
		
	}
	
	public Integer getHotelId() {
		return hotelId;
	}

	public void setHotelId(Integer hotelId) {
		this.hotelId = hotelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getHotelPicture() {
		return hotelPicture;
	}

	public void setHotelPicture(String hotelPicture) {
		this.hotelPicture = hotelPicture;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}
}
