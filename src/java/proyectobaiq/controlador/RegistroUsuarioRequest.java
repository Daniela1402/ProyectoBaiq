/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectobaiq.controlador;
import proyectobaiq.entity.Usuario;
import proyectobaiq.facade.UsuarioFacadeLocal;
import proyectobaiq.facade.UsuarioFacade;
import proyectobaiq.utilidades.Email;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daniela
 */
@Named(value = "registroUsuarioRequest")
@RequestScoped
public class RegistroUsuarioRequest implements Serializable {

    @EJB
    UsuarioFacadeLocal usuarioFacadeLocal;

    private Usuario usReg = new Usuario();
    private String correoRecuperar = "";

    /**
     * Creates a new instance of RegistroUsuarioRequest
     */
    public RegistroUsuarioRequest() {
    }

    public void crearUsuario() {
        String mensajeSw = "";
        try {
            usReg.setFechaNacimiento(new Date());
            usuarioFacadeLocal.create(usReg);
            mensajeSw = "swal('Usuario registrado' , ' con exito ', 'success')";
        } catch (Exception e) {
            mensajeSw = "swal('El usuario' , ' Ya se encuentra registrado  ', 'error')";
        }
        usReg = new Usuario();
        PrimeFaces.current().executeScript(mensajeSw);
    }

    public void recuperarClave() {
        Usuario usuRecuperar = new Usuario();
        String mensajeSw = "";
        try {
            usuRecuperar = usuarioFacadeLocal.recuperarClave(correoRecuperar);
            if (usuRecuperar.getNombres()== null) {
                mensajeSw = "swal('El correo' , ' No se encuentra registrado  ', 'error')";
            } else {

                Email.sendClaves(usuRecuperar.getCorreoElectronico(),
                        usuRecuperar.getNombres()+ " " + usuRecuperar.getApellidos(),
                        usuRecuperar.getCorreoElectronico(),
                        usuRecuperar.getContrasenia());

                mensajeSw = "swal('La clave es enviada' , ' A su correo electronico  ', 'success')";
            }
        } catch (Exception e) {
            mensajeSw = "swal('El correo' , ' No se encuentra registrado  ', 'error')";
        }
        PrimeFaces.current().executeScript(mensajeSw);
    }

    public Usuario getUsReg() {
        return usReg;
    }

    public void setUsReg(Usuario usReg) {
        this.usReg = usReg;
    }

    public String getCorreoRecuperar() {
        return correoRecuperar;
    }

    public void setCorreoRecuperar(String correoRecuperar) {
        this.correoRecuperar = correoRecuperar;
    }

}