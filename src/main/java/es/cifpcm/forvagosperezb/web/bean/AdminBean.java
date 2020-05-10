/**
 * 
 */
package es.cifpcm.forvagosperezb.web.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cifpcm.forvagosperezb.web.data.DaoFactory;
import es.cifpcm.forvagosperezb.web.data.HotelOffersDao;
import es.cifpcm.forvagosperezb.web.model.HotelOffer;
import es.cifpcm.forvagosperezb.web.model.Municipio;
import es.cifpcm.forvagosperezb.web.model.Provincia;

/**
 * @author Brian Pérez Ramos
 *
 */
@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(AdminBean.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -7548100708450673759L;
	private HotelOffersDao hotelDao;
	private List<HotelOffer> listadoHoteles = new ArrayList<>();
	private HotelOffer hotelEscogido;
	private HotelOffer hotelInsertar;
	private Integer idMunicipio = -3;
	private List<Municipio> municipios;
	private List<Provincia> provincias;
	private List<String> listaImagenes = new ArrayList<>();

	@Inject
	MasterDataBean masterDataBean;

	public AdminBean() {
		super();

	}

	/*
	 * Al establecer una sesión, se cargan en memoria los municipios y provincias que ya ha cargado MasterDataBean, 
	 * para usarlos en el Editar o Agregar Hotel.
	 * 
	 * Además, se inicializa la variable que almacene el Hotel a insertar.
	 * 
	 * Por último, se carga el listado de imágenes del servidor, desde la Base de Datos, puesto que el método de inserción/subir archivo que empleamos
	 * en la práctica GaleriaImagenes, hace que lo que hayamos subido se borre al reiniciar el servidor. Para que sea permanente, se debe 
	 * guardar en una carpeta externa al .war del proyecto (en el servidor, su sistema operativo).
	 */
	@PostConstruct
	public void init() {
		this.municipios = masterDataBean.getMunicipios();
		this.provincias = masterDataBean.getProvincias();
		this.hotelDao = DaoFactory.getInstance().getHotelOffersDao();
		this.listadoHoteles.clear();
		this.listadoHoteles.addAll(this.hotelDao.getOfertas(idMunicipio));
		this.hotelInsertar = new HotelOffer();

		logger.info("Largo de ofertas: " + this.listadoHoteles.size());
		for (HotelOffer h : this.listadoHoteles) {
			this.listaImagenes.add(h.getHotelPicture());
		}
	}

	// Simplemente recarga la lista de Hoteles para que al insertar o eliminar uno, no haya ningun error.
	public List<HotelOffer> listadoHotelesActualizado() {
		this.listadoHoteles.clear();
		this.listadoHoteles.addAll(this.hotelDao.getOfertas(idMunicipio));
		return this.listadoHoteles;
	}

	// Método para añadir un hotel, con sus validaciones.
	public String addHotel() {
		FacesContext context = FacesContext.getCurrentInstance();
		String redireccion = "hotelAdmin";
		Integer nErrores = 0;
		
		if (this.hotelInsertar.getName().trim().length() == 0) {
			nErrores++;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Debe introducir un nombre de hotel."));
		} 
		
		if(this.hotelInsertar.getPrice().floatValue() <= 0) {
			nErrores++;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El precio por noche debe ser mayor que 0!."));
		}
		
		if(nErrores == 0) {
			Boolean insertado = this.hotelDao.addHotel(this.hotelInsertar);
			if (insertado) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito!", "Ha insertado el hotel " + this.hotelInsertar.getName() + "correctamente."));
				
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha podido insertar el hotel. Contacte a un administrador."));
			}	
		}
		return redireccion;
	}

	// Función simple para dar formato a los números de los precios de cada hotel. Se 
	// redondean a 2 decimales.
	public BigDecimal formateaNumero(BigDecimal recibido) {
		return recibido.setScale(2, RoundingMode.HALF_UP);
	}

	public void removeHotel(HotelOffer hotelDelete) {
		FacesMessage msg;
		this.hotelDao = DaoFactory.getInstance().getHotelOffersDao();
		Boolean borrado = this.hotelDao.deleteHotel(hotelDelete);
		if (borrado) {
			this.listadoHoteles.remove(hotelDelete);
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
					"Ha eliminado con éxito el hotel " + hotelDelete.getName());
		} else {
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Error!",
					"No se ha podido eliminar el hotel " + hotelDelete.getName() + ". Contacte con un administrador.");
		}
		PrimeFaces.current().dialog().showMessageDynamic(msg);
	}

	// Al editar también se tienen en cuenta las validaciones.
	public String editHotel() {
		String redireccion = "hotelAdmin";
		FacesContext context = FacesContext.getCurrentInstance();
		Integer nErrores = 0;
		if (this.hotelEscogido.getName().trim().length() == 0) {
			nErrores++;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Debe introducir un nombre de hotel."));
		} 
		
		if(this.hotelEscogido.getPrice().floatValue() <= 0) {
			nErrores++;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El precio por noche debe ser mayor que 0!."));
		}
		
		if(nErrores == 0) {
			Boolean editado = this.hotelDao.editHotel(this.hotelEscogido);
			if (editado) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito!", "Ha editado el hotel correctamente."));
				redireccion = "hotelAdmin";
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se ha podid editar el hotel."));
			}
		}
		return redireccion;
	}

	// Devuelve la lista de municipios correspondiente a la provincia escogida (EDITAR HOTEL)
	public List<Municipio> cambioProvincia() {
		List<Municipio> listaDevolver;

		if (this.hotelEscogido.getIdProvincia() == null) {
			logger.info("ES NULA LA PROVINCIA");
			return masterDataBean.getMunicipios();
		} else {
			logger.info("YA NO ES NULA LA PROVINCIA");
			logger.info("LARGO LISTA ORIGINAL: " + municipios.size());
			listaDevolver = this.municipios.stream()
					.filter(m -> m.getIdProvincia().equals(this.hotelEscogido.getIdProvincia()))
					.collect(Collectors.toList());

			logger.info("LARGO LISTA: " + listaDevolver.size());
			logger.info("VALOR IDPROVINCIA: " + this.hotelEscogido.getIdProvincia());
			return listaDevolver;
		}
	}

	// Devuelve la lista de municipios correspondiente a la provincia escogida (INSERTAR HOTEL)
	public List<Municipio> cambioProvinciaDos() {
		List<Municipio> listaDevolver;

		if (this.hotelInsertar.getIdProvincia() == null) {
			logger.info("ES NULA LA PROVINCIA");
			return masterDataBean.getMunicipios();
		} else {
			logger.info("YA NO ES NULA LA PROVINCIA");
			logger.info("LARGO LISTA ORIGINAL: " + municipios.size());
			listaDevolver = this.municipios.stream()
					.filter(m -> m.getIdProvincia().equals(this.hotelInsertar.getIdProvincia()))
					.collect(Collectors.toList());

			logger.info("LARGO LISTA: " + listaDevolver.size());
			logger.info("VALOR IDPROVINCIA: " + this.hotelInsertar.getIdProvincia());
			return listaDevolver;
		}
	}

	// Devuelve la imagen escogida en el select de imágenes proporcionado al INSERTAR un hotel
	public String cambioImagen() {
		if (this.hotelInsertar.getHotelPicture() == null) {
			return this.listaImagenes.get(0);
		} else {
			return this.hotelInsertar.getHotelPicture();
		}
	}

	// Devuelve la imagen escogida en el select de imágenes proporcionado al EDITAR un hotel
	public String cambioImagenEdit() {
		if (this.hotelEscogido.getHotelPicture() == null) {
			return this.listaImagenes.get(0);
		} else {
			return this.hotelEscogido.getHotelPicture();
		}
	}
	
	public HotelOffersDao getHotelDao() {
		return hotelDao;
	}

	public void setHotelDao(HotelOffersDao hotelDao) {
		this.hotelDao = hotelDao;
	}

	public List<HotelOffer> getListadoHoteles() {
		return listadoHoteles;
	}

	public void setListadoHoteles(List<HotelOffer> listadoHoteles) {
		this.listadoHoteles = listadoHoteles;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public HotelOffer getHotelEscogido() {
		return hotelEscogido;
	}

	public void setHotelEscogido(HotelOffer hotelEscogido) {
		this.hotelEscogido = hotelEscogido;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}

	public List<Provincia> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}

	public HotelOffer getHotelInsertar() {
		return hotelInsertar;
	}

	public void setHotelInsertar(HotelOffer hotelInsertar) {
		this.hotelInsertar = hotelInsertar;
	}

	public List<String> getListaImagenes() {
		return listaImagenes;
	}

	public void setListaImagenes(List<String> listaImagenes) {
		this.listaImagenes = listaImagenes;
	}
}
