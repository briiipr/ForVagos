/**
 * 
 */
package es.cifpcm.forvagosperezb.web.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import es.cifpcm.forvagosperezb.web.data.CustomerDao;
import es.cifpcm.forvagosperezb.web.data.DaoFactory;
import es.cifpcm.forvagosperezb.web.model.Customer;

/**
 * @author Brian Pérez Ramos
 *
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -84082698524067902L;
	private Customer customerInsertar;
	private Customer customerLogeado;
	private CustomerDao cusDao;
	private Date fechaMaxima;
	private String userNameLogin;
	private String userPasswordLogin;

	public LoginBean() {

	}

	// Se inicializan tanto el customer a logear como el de insertar, para poder realizar comprobaciones en base a sus valores/estados.
	// Además, se establece como hoy la fecha máxima para el calendar de la Fecha de Nacimiento al registrar.
	@PostConstruct
	public void init() {
		customerInsertar = new Customer();
		customerLogeado = new Customer();
		this.cusDao = DaoFactory.getInstance().getCustomerDao();
		Date hoy = new Date();
		this.fechaMaxima = new Date(hoy.getTime());
	}

	// Se recogen datos del formulario y se realizan las correspondientes comprobaciones.
	public String customerLogin() {
		String redireccion = "/index.xhtml";
		FacesContext context = FacesContext.getCurrentInstance();
		Integer nErrores = 0;

		if (this.customerLogeado.getUserName().trim().length() == 0) {
			nErrores++;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Debe introducir un nombre de usuario."));
		}
		if (this.customerLogeado.getUserName().indexOf(".") == -1) {
			nErrores++;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "El nombre de usuario debe contener un punto '.'"));
		}
		if (this.customerLogeado.getPassword().trim().length() == 0) {
			nErrores++;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Debe introducir una contraseña."));
		}
		if(nErrores == 0) {
			this.customerLogeado = this.cusDao.getCustomer(this.customerLogeado.getUserName(), this.customerLogeado.getPassword());
			if(this.customerLogeado != null) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito!", "Se ha logeado satisfactoriamente."));
			}
		}
		return redireccion;
	}

	// Se recogen datos del formulario y se realizan las correspondientes comprobaciones.
	// Además, se "reinicia" el Customer a Insertar, para que al dar a Register otra vez no aparezcan
	// los datos del anterior.
	public String registerCustomer() {
		String redireccion = "";
		FacesContext context = FacesContext.getCurrentInstance();
		Integer nErrores = 0;
		if (this.customerInsertar.getFirstName().trim().length() == 0) {
			nErrores++;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Debe introducir un nombre."));
		}
		if (this.customerInsertar.getLastName().trim().length() == 0) {
			nErrores++;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Debe introducir un apellido."));
		}
		if (this.customerInsertar.getLastName().trim().length() == 0) {
			nErrores++;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Debe introducir una contraseña."));
		}
		if (this.customerInsertar.getTelefono().trim().length() == 0) {
			nErrores++;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Debe introducir un número de teléfono."));
		}
		if (this.customerInsertar.getEmail().trim().length() == 0) {
			nErrores++;
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Debe introducir un email."));
		}

		if (nErrores == 0) {
			this.customerInsertar
					.setUserName(this.customerInsertar.getFirstName() + "." + this.customerInsertar.getLastName());
			Boolean insertado = this.cusDao.addCustomer(this.customerInsertar);
			if (insertado) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades!",
						"Se ha registrado correctamente."));
				redireccion = "/index.xhtml";
			}
		}
		resetCustomerRegister();
		return redireccion;
	}

	private void resetCustomerRegister() {
		this.customerInsertar = null;
		this.customerInsertar = new Customer();
	}

	// Función para comprobar si hay un usuario logeado o no. En la plantilla hay botones para Iniciar sesion, Registrarse, etc,
	// que se muestran u ocultan en función de lo que devuelva. Esto no funciona porque no se actualiza de ninguna manera.
	public Boolean compruebaLogeado() {
		Boolean devolver = false;
		if(DaoFactory.getInstance().getConnection() == null) {
			devolver = false;
		}else if (!(this.customerLogeado.getUserName() == null)) {
			if (this.customerLogeado.getUserName().trim().length() > 0) {
				devolver = true;
			}
		}
		return devolver;
	}

	public Customer getCustomerInsertar() {
		return customerInsertar;
	}

	public void setCustomerInsertar(Customer customerInsertar) {
		this.customerInsertar = customerInsertar;
	}

	public Customer getCustomerLogeado() {
		return customerLogeado;
	}

	public void setCustomerLogeado(Customer customerLogeado) {
		this.customerLogeado = customerLogeado;
	}

	public String getUserNameLogin() {
		return userNameLogin;
	}

	public void setUserNameLogin(String userNameLogin) {
		this.userNameLogin = userNameLogin;
	}

	public String getUserPasswordLogin() {
		return userPasswordLogin;
	}

	public void setUserPasswordLogin(String userPasswordLogin) {
		this.userPasswordLogin = userPasswordLogin;
	}

	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}
}
