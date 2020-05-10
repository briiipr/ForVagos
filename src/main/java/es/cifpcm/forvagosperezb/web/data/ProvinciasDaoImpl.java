/**
 * 
 */
package es.cifpcm.forvagosperezb.web.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cifpcm.forvagosperezb.web.model.Provincia;


/**
 * @author Brian Pérez Ramos
 *
 */
public class ProvinciasDaoImpl implements ProvinciasDao{
	static Logger logger = LoggerFactory.getLogger(ProvinciasDaoImpl.class);
	private ConnectionProvider cp = DaoFactory.getInstance();
	private Connection conexion;
	private PreparedStatement pstmt = null;

	public ProvinciasDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Provincia> selectAll(){
		List<Provincia> listaProvincias = new ArrayList<>();
		try {
			conexion = cp.getConnection();
			logger.info("Estableciendo conexión");
				String consultaProvincias = "SELECT id_provincia, nombre FROM provincias";
				pstmt = conexion.prepareStatement(consultaProvincias);
				logger.info("Intentando la consulta de Provincias...");
				ResultSet resultSet = pstmt.executeQuery();
				logger.info("Consulta completada.");
				while(resultSet.next()) {
					Provincia provinciaAgregar = new Provincia();
					provinciaAgregar.setId(resultSet.getInt("id_provincia"));
					provinciaAgregar.setNombre(resultSet.getString("nombre"));
					listaProvincias.add(provinciaAgregar);
				}
			
		}catch (SQLException e) {
			logger.error("Error: " + e.getMessage());
		}
		
		return listaProvincias;
	}
}
