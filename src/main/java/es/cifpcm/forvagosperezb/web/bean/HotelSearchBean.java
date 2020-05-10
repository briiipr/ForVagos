/**
 * 
 */
package es.cifpcm.forvagosperezb.web.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
// Usar el enterprise
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cifpcm.forvagosperezb.web.model.Municipio;
import es.cifpcm.forvagosperezb.web.model.Provincia;

/**
 * @author Brian Pérez Ramos
 *
 */

@Named(value = "hotelSearchBean")
@RequestScoped
public class HotelSearchBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7950613030567033761L;
	private static Logger logger = LoggerFactory.getLogger(HotelSearchBean.class);
	private Integer idProvincia;
	private Integer idMunicipio;
	private final long dia = 24 * 60 * 60 * 1000;
	private List<Municipio> municipios;
	private List<Provincia> provincias;
	private Date fechaEscogida;
	private Date fechaMaximaEscogida;
	private Date fechaMinima;
	private Date fechaMaxima;
	private Date fechaMinimaNueva = fechaMinima;
	private Date fechaMaximaNueva;
	private Date fechaFinal;

	public HotelSearchBean() {
	}

	@Inject
	MasterDataBean masterDataBean;

	// Se establece la fecha de hoy como el mínimo para reserva (visualmente en el
	// calendar). Pero se descarta
	// a nivel de procesamiento.
	@PostConstruct
	public void init() {
		this.municipios = masterDataBean.getMunicipios();
		this.provincias = masterDataBean.getProvincias();
		Date hoy = new Date();
		fechaMinima = new Date(hoy.getTime());
		fechaMaxima = new Date(hoy.getTime() + (30 * dia));
		fechaMinimaNueva = fechaMinima;
		logger.info("VALOR FECHA MINIMA: " + this.fechaMinima);
	}

	public List<Municipio> cambioProvincia() {
		logger.info("VALOR EN CAMBIOPROVINCIA: " + this.idProvincia);
		List<Municipio> listaDevolver;

		if (this.idProvincia == null) {
			logger.info("ES NULA LA PROVINCIA");
			return masterDataBean.getMunicipios();
		} else {
			logger.info("YA NO ES NULA LA PROVINCIA");
			logger.info("LARGO LISTA ORIGINAL: " + municipios.size());
			if (this.idProvincia == -3) {
				listaDevolver = masterDataBean.getMunicipios();
			} else {
				listaDevolver = this.municipios.stream().filter(m -> m.getIdProvincia().equals(this.idProvincia))
						.collect(Collectors.toList());
			}
			logger.info("LARGO LISTA: " + listaDevolver.size());
			logger.info("VALOR IDPROVINCIA: " + this.getIdProvincia());
			return listaDevolver;
		}
	}

	// Se actualiza la fecha mínima y máxima del segundo calendario cuando escogemos fecha en el primero.
	public void cambioFechaEntrada() {
		fechaMaximaNueva = new Date(fechaEscogida.getTime() + (30 * dia));
		fechaMinimaNueva = new Date(fechaEscogida.getTime() + (dia));
	}

	public Integer getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public List<Provincia> getProvincias() {
		return this.provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}

	public List<Municipio> getMunicipios() {
		return this.municipios;

	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}

	public Date getFechaEscogida() {
		return fechaEscogida;
	}

	public void setFechaEscogida(Date fechaEscogida) {
		this.fechaEscogida = fechaEscogida;
	}

	public Date getFechaMaximaEscogida() {
		return fechaMaximaEscogida;
	}

	public void setFechaMaximaEscogida(Date fechaMaximaEscogida) {
		this.fechaMaximaEscogida = fechaMaximaEscogida;
	}

	public Date getFechaMinima() {
		return fechaMinima;
	}

	public void setFechaMinima(Date fechaMinima) {
		this.fechaMinima = fechaMinima;
	}

	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	public Date getFechaMinimaNueva() {
		return fechaMinimaNueva;
	}

	public void setFechaMinimaNueva(Date fechaMinimaNueva) {
		this.fechaMinimaNueva = fechaMinimaNueva;
	}

	public Date getFechaMaximaNueva() {
		return fechaMaximaNueva;
	}

	public void setFechaMaximaNueva(Date fechaMaximaNueva) {
		this.fechaMaximaNueva = fechaMaximaNueva;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
}
