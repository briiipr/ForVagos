/**
 * 
 */
package es.cifpcm.forvagosperezb.web.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.cifpcm.forvagosperezb.web.model.HotelOffer;

/**
 * @author Brian Pérez Ramos
 *
 */
public class HotelOffersDaoImpl implements HotelOffersDao {

	private static Logger logger = LoggerFactory.getLogger(HotelOffersDaoImpl.class);
	private ConnectionProvider cp = DaoFactory.getInstance();
	private Connection conexion;
	private PreparedStatement pstmt = null;
	private List<HotelOffer> ofertasEncontradas;

	@Override
	public List<HotelOffer> getOfertas(Integer idMunicipio) {
		ofertasEncontradas = new ArrayList<>();
		try {
			String consultaHotelOffers;
			conexion = cp.getConnection();
			logger.info("Estableciendo conexión");

			if (idMunicipio == -3) {
				consultaHotelOffers = "SELECT hotel_id,name,price,hotel_picture,m.id_provincia,m.id_municipio"
						   + " FROM hoteloffer as h INNER JOIN municipios as m ON h.id_municipio = m.id_municipio";
				pstmt = conexion.prepareStatement(consultaHotelOffers);
			} else {
				consultaHotelOffers = "SELECT hotel_id,name,price,hotel_picture,m.id_provincia,m.id_municipio"
								   + " FROM hoteloffer as h INNER JOIN municipios as m ON h.id_municipio = m.id_municipio"
								   + " WHERE h.id_municipio = ?";
				pstmt = conexion.prepareStatement(consultaHotelOffers);
				pstmt.setInt(1, idMunicipio);
			}
			logger.info("Ejecutando consulta HotelOffers");
			ResultSet rs = pstmt.executeQuery();
			logger.info("HOTEL ENCONTRADO. ");
			while (rs.next()) {
				HotelOffer ofertaAgregar = new HotelOffer();
				ofertaAgregar.setHotelId(rs.getInt("hotel_id"));
				ofertaAgregar.setName(rs.getString("name"));
				ofertaAgregar.setPrice(BigDecimal.valueOf(rs.getFloat("price")));
				ofertaAgregar.setHotelPicture(rs.getString("hotel_picture"));
				ofertaAgregar.setIdProvincia(rs.getInt("id_provincia"));
				ofertasEncontradas.add(ofertaAgregar);
			}
		} catch (SQLException e) {
			logger.error("Error: " + e);
		}
		return ofertasEncontradas;
	}

	@Override
	public Boolean deleteHotel(HotelOffer hotelDelete) {
		try {
			String consultaDeleteHotel;
			conexion = cp.getConnection();
			logger.info("Estableciendo conexión");

			consultaDeleteHotel = "DELETE FROM hoteloffer WHERE hotel_id = ?";
			pstmt = conexion.prepareStatement(consultaDeleteHotel);
			pstmt.setInt(1, hotelDelete.getHotelId());
			logger.info("Ejecutando consulta de eliminación del hotel");
			int devuelto = pstmt.executeUpdate();
			if (devuelto == 1) {
				logger.info("Hotel " + hotelDelete.getName() + " eliminado satisfactoriamente.");
				return true;
			} else {
				logger.error("Error: no se ha podido eliminar el hotel");
			}
		} catch (SQLException e) {
			logger.error("Error: " + e);
		}
		return false;
	}

	@Override
	public Boolean addHotel(HotelOffer hotelAdd) {
		try {
			String consutlaAddHotel;
			conexion = cp.getConnection();
			logger.info("Estableciendo conexión AÑADIR HOTEL");

			consutlaAddHotel = "INSERT INTO hoteloffer (name, price, hotel_picture, id_municipio) VALUES (?,?,?,?)";

			pstmt = conexion.prepareStatement(consutlaAddHotel);
			pstmt.setString(1, hotelAdd.getName());
			pstmt.setFloat(2, hotelAdd.getPrice().floatValue());
			pstmt.setString(3, hotelAdd.getHotelPicture());
			pstmt.setInt(4, hotelAdd.getIdMunicipio());
			logger.info("Ejecutando consulta de insertado del hotel");
			int devuelto = pstmt.executeUpdate();
			if (devuelto == 1) {
				logger.info("Hotel " + hotelAdd.getName() + " agregado satisfactoriamente.");
				return true;
			} else {
				logger.error("Error: no se ha podido agregar el hotel");
			}
		} catch (SQLException e) {
			logger.error("Error: " + e);
		}
		return false;
	}

	@Override
	public Boolean editHotel(HotelOffer hotelEdit) {
		try {
			String consutlaEditHotel;
			conexion = cp.getConnection();
			logger.info("Estableciendo conexión EDITAR HOTEL");

			consutlaEditHotel = "UPDATE hoteloffer SET name = ?, price = ?, hotel_picture = ?, id_municipio = ? WHERE hotel_id = ?";

			pstmt = conexion.prepareStatement(consutlaEditHotel);
			pstmt.setString(1, hotelEdit.getName());
			pstmt.setFloat(2, hotelEdit.getPrice().floatValue());
			pstmt.setString(3, hotelEdit.getHotelPicture());
			pstmt.setInt(4, hotelEdit.getIdMunicipio());
			pstmt.setInt(5, hotelEdit.getHotelId());
			logger.info("Ejecutando consulta de EDITADO del hotel");
			int devuelto = pstmt.executeUpdate();
			if (devuelto == 1) {
				logger.info("Hotel " + hotelEdit.getName() + " actualizado satisfactoriamente.");
				return true;
			} else {
				logger.error("Error: no se ha podido eliminar el hotel");
			}
		} catch (SQLException e) {
			logger.error("Error: " + e);
		}
		return false;
	}
}
