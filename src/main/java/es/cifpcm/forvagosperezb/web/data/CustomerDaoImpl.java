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

import es.cifpcm.forvagosperezb.web.model.Customer;

/**
 * @author Brian Pérez Ramos
 *
 */
public class CustomerDaoImpl implements CustomerDao {
	private static Logger logger = LoggerFactory.getLogger(CustomerDaoImpl.class);
	private ConnectionProvider cp = DaoFactory.getInstance();
	private Connection conexion;
	private PreparedStatement pstmt = null;
	private List<Customer> listaCustomers;

	
	@Override
	public Customer getCustomer(String userName, String passWord) {
		Customer customerDevolver = new Customer();
		try {
			Boolean encontrado = false;
			String[] userNameEncontrado = new String[2];
			String consultaGetUser = "SELECT * FROM users as u WHERE u.user_name = ? AND u.password = ? ";
			conexion = cp.getConnection();
			logger.info("Estableciendo conexion getUser");
			pstmt = conexion.prepareStatement(consultaGetUser);
			pstmt.setString(1, userName);
			pstmt.setString(2, passWord);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				logger.info("EL STRING DEVUELTO SEPARADO ES: " + rs.getString("user_name").split("\\."));
				userNameEncontrado = rs.getString("user_name").split("\\.");
				customerDevolver.setUserId(rs.getInt("user_id"));
				encontrado = true;
			}else {
				customerDevolver = null;
			}
			
			if(encontrado == true) {
				String consultaGetCustomer = "SELECT * FROM customer as c WHERE c.first_name = ? AND c.last_name = ?";
				conexion = cp.getConnection();
				logger.info("Estableciendo conexion getCustomer");
				pstmt = conexion.prepareStatement(consultaGetCustomer);
				pstmt.setString(1, userNameEncontrado[0]);
				pstmt.setString(2, userNameEncontrado[1]);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					customerDevolver.setCustomerId(rs.getInt("customer_id"));
					customerDevolver.setFirstName(rs.getString("first_name"));
					customerDevolver.setLastName(rs.getString("last_name"));
					customerDevolver.setTelefono(rs.getString("telefono"));
					customerDevolver.setEmail(rs.getString("email"));
					customerDevolver.setFechaNacimiento(rs.getDate("fecha_de_nacimiento"));
				}else {
					customerDevolver = null;
				}
			}
		}catch(SQLException e) {
			logger.error("Error: " + e);
		}
		return customerDevolver;
	}
	
	@Override
	public Boolean addCustomer(Customer customerAdd) {
		Boolean devolver = false;
		try {
			Integer userIdUsar = 0;
			String consultaGetMaxUserId = "SELECT MAX(user_id) FROM users";
			conexion = cp.getConnection();
			logger.info("Estableciendo conexion addCustomer");
			pstmt = conexion.prepareStatement(consultaGetMaxUserId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				userIdUsar = rs.getInt(1) + 1;
			}
			
			if(userIdUsar != 0) {
				conexion = cp.getConnection();
				logger.info("Estableciendo conexion insert user");
				String consultaInsertUser = "INSERT INTO users (user_id,user_name,password) VALUES (?,?,?)";
				pstmt = conexion.prepareStatement(consultaInsertUser);
				pstmt.setInt(1, userIdUsar);
				pstmt.setString(2, customerAdd.getUserName());
				pstmt.setString(3, customerAdd.getPassword());
				int devuelto = pstmt.executeUpdate();
				
				if(devuelto == 1) {
					String consultaInsertCustomer = "INSERT INTO customer (first_name,last_name,telefono,email,fecha_de_nacimiento) VALUES (?,?,?,?,?)";
					conexion = cp.getConnection();
					logger.info("Estableciendo conexion insert customer");
					pstmt = conexion.prepareStatement(consultaInsertCustomer);
					pstmt.setString(1, customerAdd.getFirstName());
					pstmt.setString(2, customerAdd.getLastName());
					pstmt.setString(3, customerAdd.getTelefono());
					pstmt.setString(4, customerAdd.getEmail());
					java.sql.Date fechaIntroducir = new java.sql.Date(customerAdd.getFechaNacimiento().getTime());
					pstmt.setDate(5, fechaIntroducir);
					int devuelto2 = pstmt.executeUpdate();
					if(devuelto2 == 1) {
						devolver = true;
					}else {
						logger.info("No se ha podido insertar en la tabla Customer.");
					}
				}else {
					logger.info("No se ha podido insertar en la tabla Users");
				}
				
			}
		}catch (SQLException e) {
			logger.info("Error: " + e);
		}
		return devolver;
	}
	@Override
	public List<Customer> getAll(Integer customerId, Integer userId) {
		listaCustomers = new ArrayList<>();
		
		// Sin implementar, dado que no se ha implementado antes el menú de administración de Usuarios.
		
		return listaCustomers;
	}
	
	

	@Override
	public Boolean deleteCustomer(Customer customerDelete) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Boolean editCustomer(Customer customerEdit) {
		// TODO Auto-generated method stub
		return null;
	}



}
