/**
 * 
 */
package es.cifpcm.forvagosperezb.web.data;

import java.util.List;

import es.cifpcm.forvagosperezb.web.model.HotelOffer;

/**
 * @author Brian Pérez Ramos
 *
 */
public interface HotelOffersDao {
	public List<HotelOffer> getOfertas(Integer idMunicipio);
	public Boolean deleteHotel(HotelOffer hotelDelete);
	public Boolean addHotel(HotelOffer hotelAdd);
	public Boolean editHotel(HotelOffer hotelEdit);
}
