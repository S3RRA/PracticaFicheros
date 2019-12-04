package practica_ficheros;

    public class Registro{

        protected char usuario[],contraseña[],nombre[],correo[], telefono[];
        protected int tam;

        public Registro(char[] usuario, char[] contraseña, char[] nombre, char[] correo, char[] telefono){
            this.usuario = new char[9];//9*2
            this.usuario = usuario;
            
            
            this.contraseña = new char[20];//20*2
            this.contraseña = contraseña;
            
            this.nombre = new char[15];//15*2
            this.nombre = nombre;
            
            this.correo = new char[40];//40*2
            this.correo = correo;
            
            this.telefono = new char[9];//9*2
            this.telefono = telefono;
            
            tam=186;
        }        
    }    
       

   