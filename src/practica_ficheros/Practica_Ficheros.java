//AUTOR: Pablo Serrano Manzarbeitia
package practica_ficheros;
import java.io.*;
import java.util.Scanner;
public class Practica_Ficheros
{
    public static void main(String[] args) throws IOException{
        System.err.println("Por seguridad tendrá que introducir sus credenciales antes de cada operación.");
        File fichero = new File("clientes.txt");
        
        login(fichero);

    }
    
    public static void login(File fichero) throws IOException{
        Scanner sc = new Scanner (System.in);
        System.out.println("¿Dispone de una cuenta? \n a)Si \n b)No");
        String respuesta = sc.nextLine();
        if(respuesta.equals("SI")||respuesta.equals("si") || respuesta.equals("Si")){
            System.out.println("-------------------------------------- \n LOGIN: \n-------------------------------------- \nDNI: ");
            String usuario = sc.nextLine();
            System.out.println("Contraseña: ");
            String contraseña = sc.nextLine();
            System.out.println("--------------------------------------");
            tengoCuenta(usuario, contraseña, sc);
        } else if(respuesta.equals("No")||respuesta.equals("NO")||respuesta.equals("no")){
           newUser(fichero);
        }       
    }
    public static void tengoCuenta(String usuario, String contraseña, Scanner sc) throws IOException{
        //Comparar DNI+contraseña insertados con todos los campos DNI y contraseña de clientes.txt
        //Cogemos todos los DNI
        File fichero = new File("clientes.txt");
        RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
        char[] Contraseña = new char[20];
        for(int i=0;i<contraseña.length();i++){
            Contraseña[i] = contraseña.charAt(i);            
        }
        String ContraseñaMishue = new String (Contraseña);
        int longitud = (int)fichero.length();    
        int tamañoRegistro = 186;
        int registros = longitud/tamañoRegistro;
        boolean correcto = false;        
         if (registros>0){//VECTOR = ARRAY
            Registro vector[] = leerRegistro(raf, registros,tamañoRegistro);
            
            for(int i=0; i< registros; i++){
                String usuarioG = new String (vector[i].usuario);
                String contraseñaG = new String (vector[i].contraseña);
                if(usuarioG.equals(usuario) && contraseñaG.equals(ContraseñaMishue)){  
                    correcto = true;
                }
            }
        }
         
        if(correcto){
            System.out.println("Bienvenido! \n--------------------------------------");
            System.out.println("¿Que desea realizar? \n a)Leer los registros(Inserte '1') \n b)Añadir un registro(Inserte '2') \n c)Borrar un registro(Inserte '3')" );
            String respuesta = sc.nextLine();
            if(respuesta.equals("1")){
                leerDatos(raf,fichero);
            }else if(respuesta.equals("2")){
                newUser(fichero);
            }else if(respuesta.equals("3")){
                borraDatos(fichero, raf, sc);
            }
        }else{
            System.err.println("Usuario o contraseña incorrecto palomo!!");
        }
        
    }
    public static void newUser(File fichero) throws IOException{
        Scanner sc = new Scanner (System.in);
        System.out.println("-------------------------------------- \nUSUARIO NUEVO \n-------------------------------------- \nDNI:");
        String usuario = sc.nextLine();
        System.out.println("Contraseña: ");
        String contraseña = sc.nextLine();
        System.out.println("Repita su contraseña: ");
        String contraseña2 = sc.nextLine();
        System.out.println("Indique su nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Indique su correo electrónico: ");
        String correo = sc.nextLine();
        System.out.println("Indique un teléfono de contacto: ");
        String telefono = sc.nextLine(); 
        if(contraseña.equals(contraseña2)){
            creaUsuario(usuario,contraseña,nombre,correo,telefono);
        } else{
            System.out.println("Las contraseñas deben coincidir.");
            System.out.println("--------------------------------------");
            newUser(fichero);
        }
        System.out.println("--------------------------------------");
        login(fichero);
        
    }
    
    public static void creaUsuario(String usuario,String contraseña,String nombre,String correo,String telefono) throws IOException{
        File fichero = new File("clientes.txt");
        RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
        char[] Usuario = new char[9];
        for(int i=0; i<=8; i++){
            Usuario[i] = usuario.charAt(i);            
        }

        char[] Contraseña = new char[20];
        for(int i=0;i<contraseña.length();i++){
            Contraseña[i] = contraseña.charAt(i);            
        }
        
        char[] Nombre = new char[15];
        for(int i=0;i<nombre.length();i++){
            Nombre[i] = nombre.charAt(i);
           // Nombre[i] = j;
        }
       
        char[] Correo = new char[40];
        for(int i=0;i<correo.length();i++){
            Correo[i] = correo.charAt(i);            
        }
        
        char[] Telefono = new char[9];
        for(int i=0;i<=8;i++){
            Telefono[i] = telefono.charAt(i);          
        }
     
        Registro estudiante = new Registro (Usuario,Contraseña,Nombre,Correo,Telefono);
        grabarRegistro(raf, fichero,estudiante);
                /*Usuario,Contraseña,Nombre,Correo,Telefono*/
    }
    
    public static void grabarRegistro(RandomAccessFile raf, File fichero, Registro estudiante)throws IOException{
        int longitud = (int) fichero.length();
        raf.seek(longitud);        
        for (int i=0; i < (estudiante.usuario).length ; i++){
            raf.writeChar(estudiante.usuario[i]);
        }
        for (int i=0; i < (estudiante.contraseña).length ; i++){
            raf.writeChar(estudiante.contraseña[i]);       
        }
        for (int i=0; i < (estudiante.nombre).length ; i++){
            raf.writeChar(estudiante.nombre[i]);
        }
        for (int i=0; i < (estudiante.correo).length ; i++){
            raf.writeChar(estudiante.correo[i]);
        }
        for (int i=0; i < (estudiante.telefono).length ; i++){
            raf.writeChar(estudiante.telefono[i]);
        }  
        longitud = (int)raf.length();
        System.out.println("Tamaño del archivo: "+longitud);
    }

    public static void leerDatos(RandomAccessFile raf, File fichero)throws IOException{ 
        System.out.println("\nLeyendo datos...\n");
        System.out.println("Registros de 'clientes.txt': \n");       
        int longitud = (int)raf.length();   
        int tamañoRegistro = 186;
        int registros = longitud/tamañoRegistro;
        System.out.println("Tamaño total del archivo: "+longitud+" bytes\n");
        System.out.println("Número total de registros: "+registros+"\n");
        System.out.println("Tamaño de cada registro: "+tamañoRegistro+" bytes\n");
        if (registros>0){//VECTOR = ARRAY[][]
            Registro vector[] = leerRegistro(raf, registros,tamañoRegistro);
            for(int i=0; i< registros; i++){
                String usu = new String(vector[i].usuario);
                
                String nom = new String(vector[i].nombre);
              
                String pass = new String(vector[i].contraseña);                
                 
                String corr = new String(vector[i].correo);               
                
                String num = new String(vector[i].telefono);
                  
                System.out.println("\n--------------------------------------");
                System.out.println("Registro nº "+(i+1)+":\n--------------------------------------");
                System.out.println("Usuario: "+usu+"\n"+"Contraseña: "+pass+"\n"+"Nombre: "+nom +"\n"+"Correo electrónico: "+corr+"\n"+"Teléfono de contacto: "+num+"\n");                
            }        
        }else{
            System.err.println("El archivo está vacío");
        }
        System.out.println("\n--------------------------------------");
    }
    
    public static Registro[] leerRegistro(RandomAccessFile raf, int registros, int tamaño) throws IOException{
        Registro vector[] = new Registro[registros]; 
        raf.seek(0);
        for(int i=0;i < registros; i++){
            char usuario[] = new char[9];
            for(int j=0;j<usuario.length;j++){
                usuario[j] = raf.readChar();    
            }
           
            char contraseña[] = new char[20];
            for(int j=0;j<contraseña.length;j++){
                contraseña[j] = raf.readChar();
            }
           
            char nombre[] = new char[15];
            for(int j=0;j<nombre.length;j++){
                nombre[j] = raf.readChar();
            }
           
            char correo[] = new char[40];
            for(int j=0;j<correo.length;j++){
                correo[j] = raf.readChar();
            }
            
            char telefono[] = new char[9];
            for(int j=0;j<telefono.length;j++){
                telefono[j] = raf.readChar();
            }
             
            Registro datos = new Registro(usuario, contraseña, nombre, correo, telefono); 
            
            vector[i] = datos;
        }
        return vector;
    } 
    
    public static void borraDatos(File fichero, RandomAccessFile raf, Scanner sc) throws IOException{
        // 1º -> saco todos los clientes que no quiero borrar y los guardo en un array
        // 2º -> meto todos los del array en clientes .txt de manera secuencial
        // 3º -> paso por pantalla el archivo borrado -> confirmas
        
        //1º ->(raf) Localizo en vector -> guardo todo en vector nuevo SIN la posicion localizada (raf/)
        //2º ->(br) Abro clientes -> Sobrescribo con NADA bw.write("") (br/)
        System.out.println("Introduzca el DNI del registro que desea eliminar; si desea ver todos los registros teclee 'ver':");
        String aborrar = sc.nextLine();
        if(sc.equals("ver")|sc.equals("Ver")|sc.equals("VER")){
            leerDatos(raf, fichero);
            System.out.println("Introduzca el número de registro que desea eliminar:");
            aborrar = sc.nextLine();
        }
        int pos = 0;
        int longitud = (int)fichero.length();   
        int tamañoRegistro = 186;
        int registros = longitud/tamañoRegistro;
        boolean borrar = false;
        Registro vector2[] = new Registro[registros-1];
        if(registros>0){
            Registro vector[] = leerRegistro(raf, registros, tamañoRegistro);
            for(int i=0; i<registros;i++){
                String buscame = new String(vector[i].usuario);
                if(buscame.equals(aborrar)){
                    pos = i;
                    borrar = true;
                    break;
                }
            }
            if(!borrar){
                System.out.println("No existe el registro que desea eliminar");
            }
        }
        if(borrar){
            
            raf.seek(0);
            int contador = 0;
            
            for(int i=0;i<registros;i++){
                if(i!=pos){
                    char usuario[] = new char[9];
                    char contraseña[] = new char[20];
                    char nombre[] = new char[15];
                    char correo[] = new char[40];
                    char telefono[] = new char[9];
                    
                    for(int j=0; j<9; j++){
                        usuario[j] = raf.readChar();
                    }
                    for(int j=0; j<20; j++){
                        contraseña[j] = raf.readChar();
                    }
                    for(int j=0; j<15; j++){
                        nombre[j] = raf.readChar();
                    }
                    for(int j=0; j<40; j++){
                        correo[j] = raf.readChar();
                    }
                    for(int j=0; j<9; j++){
                        telefono[j] = raf.readChar();
                    }
                    
                    Registro registraco = new Registro(usuario, contraseña, nombre, correo, telefono);
                    vector2[contador]=registraco;
                    contador++;
                    
                } else{
                    raf.seek(raf.getFilePointer()+186);
                }
            }
            reescribeDatos();
            nuevoFichero(vector2);
        }
    }
    public static void reescribeDatos() throws IOException{
        FileWriter fw = new FileWriter("clientes.txt", false);
        BufferedWriter br = new BufferedWriter(fw); 
        br.write("");
        fw.close();br.close();
    }
    public static void nuevoFichero(Registro[] vector2) throws IOException{
        File fichero = new File("clientes.txt");
        RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
        raf.seek(0);
        for(int i=0;i<vector2.length;i++){
            
            for(int j=0; j<9; j++){
                raf.writeChar(vector2[i].usuario[j]);
            }
            for(int j=0; j<20; j++){
                raf.writeChar(vector2[i].contraseña[j]);
            }
            for(int j=0; j<15; j++){
                raf.writeChar(vector2[i].nombre[j]);
            }
            for(int j=0; j<40; j++){
                raf.writeChar(vector2[i].correo[j]);
            }           
            for(int j=0; j<9; j++){
                raf.writeChar(vector2[i].telefono[j]);
            }
            
            
        }
    }
    
}