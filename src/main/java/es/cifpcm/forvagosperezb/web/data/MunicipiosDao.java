/**
 * 
 */
package es.cifpcm.forvagosperezb.web.data;

import java.util.List;

import es.cifpcm.forvagosperezb.web.model.Municipio;


/**
 * @author Brian P�rez Ramos
 *
 */
public interface MunicipiosDao {
	public List<Municipio> selectAll(Integer idProvinciaMadre);
}
