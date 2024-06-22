public class User {
    int id;
    String nombreUsuario;
    String clave;
    String nombre;
    int dni;
    String email;
    Boolean asociado;
    int cod_actividad;
    String categoria;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAsociado() {
        return asociado;
    }

    public void setAsociado(Boolean asociado) {
        this.asociado = asociado;
    }

    public int getCod_actividad() {
        return cod_actividad;
    }

    public void setCod_actividad(int cod_actividad) {
        this.cod_actividad = cod_actividad;
    }
    public String get_Categoria() {
        return categoria;
    }
    public void set_categoria(String categoria) {
        this.categoria = categoria;
    }

    public User(int id,
                String nombreUsuario,
                String clave,
                String nombre,
                int dni,
                String email,
                Boolean asociado,
                int cod_actividad) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.asociado = asociado;
        this.cod_actividad = cod_actividad;


    }
}
