/**
 * 
 */
package es.cifpcm.forvagosperezb.web.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cifpcm.forvagosperezb.web.data.DaoFactory;
import es.cifpcm.forvagosperezb.web.data.MunicipiosDao;
import es.cifpcm.forvagosperezb.web.data.ProvinciasDao;
import es.cifpcm.forvagosperezb.web.model.Municipio;
import es.cifpcm.forvagosperezb.web.model.Provincia;

/**
 * @author Brian Pérez Ramos
 *
 */

@Named(value="masterDataBean")
@ApplicationScoped
public class MasterDataBean {
	private List<Provincia> listaProvincias = new ArrayList<Provincia>();
	private List<Municipio> listaMunicipios = new ArrayList<Municipio>();
	
	static Logger logger = LoggerFactory.getLogger(MasterDataBean.class);
	
	// Se cargan la lista de municipios y la de provincias. 
	// Además, se comprueba que se pueda establecer la conexión a la base de datos, y en caso negativo, se
	// notifica al usuario.
	public MasterDataBean() {
		ProvinciasDao provDao = DaoFactory.getInstance().getProvinciasDao();
		MunicipiosDao munDao = DaoFactory.getInstance().getMunicipiosDao();
		if(DaoFactory.getInstance().getConnection() != null) {
			if(this.listaProvincias.size() == 0 && this.listaMunicipios.size() == 0) {
				this.listaProvincias.addAll(provDao.selectAll());
				this.listaMunicipios.addAll(munDao.selectAll(null));				
			}
		}else {
			this.listaMunicipios = null;
			this.listaProvincias = null;
		}
	}
	
	// Hace que la imagen de inicio y el boton de buscar funcionen o no, dependiendo de si 
	// se puede conectar a la base de datos.
	public String redireccion() {
		String redireccionD = "";
		if(DaoFactory.getInstance().getConnection() == null) {
			redireccionD = "index";
		}else {
			redireccionD = "search/index";
		}
		return redireccionD;
	}
	
	// Empleado para mostrar u ocultar al usuario el mensaje cuando no se pueda conectar a la base de datos.
	public Boolean estadoConexion() {
		if(DaoFactory.getInstance().getConnection() == null) {
			return null;
		}else {
			return true;
		}
	}
	
	public List<Provincia> getProvincias(){
		return this.listaProvincias;
	}
	
	public List<Municipio> getMunicipios(){
		return this.listaMunicipios;
	}
}
