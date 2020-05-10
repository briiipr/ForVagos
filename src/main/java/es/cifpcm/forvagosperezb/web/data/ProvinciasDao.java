/**
 * 
 */
package es.cifpcm.forvagosperezb.web.data;

import java.util.List;

import es.cifpcm.forvagosperezb.web.model.Provincia;

/**
 * @author Brian Pérez Ramos
 *
 */
public interface ProvinciasDao {
	public List<Provincia> selectAll();
}
