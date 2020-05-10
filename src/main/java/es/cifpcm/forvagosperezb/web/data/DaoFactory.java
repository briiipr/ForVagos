/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.cifpcm.forvagosperezb.web.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Brian P�rez Ramos
 */
public class DaoFactory implements ConnectionProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(DaoFactory.class);

	private static DaoFactory instance;

	private DatabaseConfig dbCfg;

	private DaoFactory() {

		ResourceBundle rb = ResourceBundle.getBundle("database");

		try {
			@SuppressWarnings("unused")
			InitialContext ctx = new InitialContext();
			dbCfg = new DatabaseConfig(rb.getString("db.driver"), rb.getString("db.url"), rb.getString("db.user"),
					rb.getString("db.pw"));

			LOGGER.debug("Pidiendo datasource: {}", dbCfg.getDatasourceName());           
		} catch (NamingException ex) {
			LOGGER.error(null, ex);
		}
	}

	public static synchronized DaoFactory getInstance() {

		if (instance == null) {
			instance = new DaoFactory();
		}

		return instance;
	}

	@Override
	public Connection getConnection() {
		try {
        	Class.forName(dbCfg.getDriverName());
            Connection conn=DriverManager.getConnection(dbCfg.getUrl(),dbCfg.getUser(),dbCfg.getPassword());
            return conn;
        } catch (SQLException ex) {
            LOGGER.error(null, ex);
            return null;
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public MunicipiosDaoImpl getMunicipiosDao() {
		return new MunicipiosDaoImpl();
	}

	public ProvinciasDaoImpl getProvinciasDao() {

		return new ProvinciasDaoImpl();
	}
	
	public HotelOffersDaoImpl getHotelOffersDao() {
		return new HotelOffersDaoImpl();
	}
	
	public CustomerDaoImpl getCustomerDao() {
		return new CustomerDaoImpl();
	}
}
