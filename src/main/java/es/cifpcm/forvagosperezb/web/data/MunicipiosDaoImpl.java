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

import es.cifpcm.forvagosperezb.web.model.Municipio;

/**
 * @author Brian Pérez Ramos
 *
 */
public class MunicipiosDaoImpl implements MunicipiosDao{
	private static Logger logger = LoggerFactory.getLogger(MunicipiosDaoImpl.class);
	private ConnectionProvider cp = DaoFactory.getInstance();
	private Connection conexion;
	private PreparedStatement pstmt = null;

	public MunicipiosDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Municipio> selectAll(Integer idProvinciaMadre){
		 List<Municipio> listaMunicipios = new ArrayList<>();
		if(idProvinciaMadre == null) {
			try {
				conexion = cp.getConnection();
				logger.info("Estableciendo conexión");
				String consultaMunicipios = "SELECT id_municipio,id_provincia,cod_municipio,DC,nombre FROM municipios";
				pstmt = conexion.prepareStatement(consultaMunicipios);
				logger.info("Intentando la consulta de Municipios...");
				ResultSet resultSet = pstmt.executeQuery();
				logger.info("Consulta completada.");
				while (resultSet.next()) {
					Municipio municipioAgregar = new Municipio();
					municipioAgregar.setIdMunicipio(resultSet.getInt("id_municipio"));
					municipioAgregar.setIdProvincia(resultSet.getInt("id_provincia"));
					municipioAgregar.setCodMunicipio(resultSet.getInt("cod_municipio"));
					municipioAgregar.setDigitoControl(resultSet.getInt("DC"));
					municipioAgregar.setNombre(resultSet.getString("nombre"));
					listaMunicipios.add(municipioAgregar);
				}
			} catch (SQLException e) {
				logger.error("Error: " + e.getMessage());
			}
		}
		return listaMunicipios;
	}
}
