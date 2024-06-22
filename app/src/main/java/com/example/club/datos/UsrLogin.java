public class UsrLogin {
    int id;
    String nombreUsuario;
    String clave;

    public UsrLogin(int id, String nombreUsuario, String clave) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
