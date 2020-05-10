/**
 * 
 */
package es.cifpcm.forvagosperezb.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cifpcm.forvagosperezb.web.data.DaoFactory;
import es.cifpcm.forvagosperezb.web.data.HotelOffersDao;
import es.cifpcm.forvagosperezb.web.model.HotelOffer;

/**
 * @author Brian Pérez Ramos
 *
 */
@Named(value="hotelResultsBean")
@SessionScoped
public class HotelResultsBean implements Serializable{
	private static Logger logger = LoggerFactory.getLogger(HotelResultsBean.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 4882613708766772408L;
	
	private HotelOffersDao hotelDao;
	private Integer idMunicipio;
	private Integer nNoches;
	private HotelOffer ofertaEscogida;
	private List<HotelOffer> hotelOffers = new ArrayList<>();
	
	public HotelResultsBean() {
		
	}

	public HotelOffer getOfertaEscogida() {
		return ofertaEscogida;
	}
	
	// Método que realiza el cálculo del número de noches, y se comprueba que no se introduzca el dia actual (lo último no funciona)
	// Una vez calculado, se filtran los hoteles en base al municipio escogido.
	public String redireccion(Integer idMunicipio, Date fechaMinima, Date fechaEntrada, Date fechaSalida) {
		long diferenciaEnMilis = Math.abs(fechaSalida.getTime() - fechaEntrada.getTime());
		this.nNoches = (int) TimeUnit.DAYS.convert(diferenciaEnMilis, TimeUnit.MILLISECONDS);
		logger.info("Numero de noches: " + this.nNoches);
		logger.info("EL VALOR DE LA FECHA DE ENTRADA ES: " + fechaEntrada.getTime());
		String mensaje = compruebaDatosBusqueda(idMunicipio, fechaMinima, fechaEntrada, fechaSalida);
		if(mensaje.length() == 0) {
			this.hotelDao = DaoFactory.getInstance().getHotelOffersDao();
			if(idMunicipio == -3) {
				logger.info("Se han seleccionado todos los municipios");
			}else {
				logger.info("Se ha seleccionado el municipio: " + idMunicipio);
			}
			this.hotelOffers.clear();
			this.hotelOffers.addAll(this.hotelDao.getOfertas(idMunicipio));
			return "searchResults";			
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensaje));
			return "";
		}
	}
	
	// Simple función para comprobar los datos introducidos.
	private String compruebaDatosBusqueda(Integer idMunicipio, Date fechaMinima, Date fechaEntrada, Date fechaSalida) {
		logger.info("EL VALOR DE LA FECHA DE ENTRADA ES: " + fechaEntrada.getTime());
		String mensajeError = "";
		if(fechaEntrada.compareTo(fechaMinima) == 0) {
			mensajeError = "No permitimos reservar para el día actual. Pruebe otra fecha.";
			logger.error("LA FECHA DE ENTRADA ES IGUAL A LA MINIMA");
		}	
		return mensajeError;
	}

	public void setOfertaEscogida(HotelOffer ofertaEscogida) {
		this.ofertaEscogida = ofertaEscogida;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Integer getnNoches() {
		return nNoches;
	}

	public void setnNoches(Integer nNoches) {
		this.nNoches = nNoches;
	}

	public List<HotelOffer> getHotelOffers() {
		return hotelOffers;
	}

	public void setHotelOffers(List<HotelOffer> hotelOffers) {
		this.hotelOffers = hotelOffers;
	}
}
