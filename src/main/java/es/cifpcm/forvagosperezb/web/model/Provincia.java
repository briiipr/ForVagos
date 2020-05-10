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
public class Provincia implements Serializable{
	private Integer id;
	private String nombre;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
