class UsuarioDB {
    var id:Int = 0
    var username:String = ""
    var password:String = ""
    var nombreApellido:String = ""
    var dni:String = ""
    var email:String = ""
    var asociado:Boolean = false
    var codAct:Int = 1
    var categoria: String = ""

    constructor(){}

    constructor(
        username: String,
        password: String,
        nombreApellido: String,
        dni: String,
        email: String,
        asociado: Boolean,
        categoria: String
    ) {
        this.username = username
        this.password = password
        this.nombreApellido = nombreApellido
        this.dni = dni
        this.email = email
        this.asociado = asociado
        this.categoria = categoria
    }

    override fun toString(): String {
        return "UsuarioDB(id=${id}, username='${username}', password='${password}', nombreApellido='${nombreApellido}', dni='${dni}', email='${email}', asociado=${asociado}, codAct=${codAct}, categ=${categoria})"
    }
}