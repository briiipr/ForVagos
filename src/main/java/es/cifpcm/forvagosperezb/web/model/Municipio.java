/**
 * 
 */
package es.cifpcm.forvagosperezb.web.model;

import java.io.Serializable;

/**
 * @author Brian Pérez Ramos
 *
 */
@SuppressWarnings("serial")
public class Municipio implements Serializable {
	private Integer idMunicipio;
	private Integer idProvincia;
	private Integer codMunicipio;
	private Integer digitoControl;
	private String nombre;
	
	public Integer getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public Integer getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}
	public Integer getCodMunicipio() {
		return codMunicipio;
	}
	public void setCodMunicipio(Integer codMunicipio) {
		this.codMunicipio = codMunicipio;
	}
	public Integer getDigitoControl() {
		return digitoControl;
	}
	public void setDigitoControl(Integer digitoControl) {
		this.digitoControl = digitoControl;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
