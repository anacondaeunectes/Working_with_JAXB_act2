package utilityPackage;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Clase que controla la entrada de datos al programa.
 * 
 * @author Pablo Gutierrez
 */
public class EntradaDatos {

	/**
	 * Scanner que crea el flujo de entrada.
	 */
	static Scanner entrada = new Scanner(System.in);

	/**
	 * Metodo que permite la entrada de un dato de tipo double por teclado
	 * @return double
	 */
	public static double pedirDouble() {
		
		double num = 0;
		boolean ok=false;
		
		do {
			try {
				num = entrada.nextDouble();
				ok=true;
				
			} catch (Exception e) {
				System.out.println("Numero no valido. Por favor, vuelva a introducirlo: ");
				ok=false;
			}
			
			entrada.nextLine();
		} while (ok==false);
		
		return num;
	}
	
	/**
	 * Metodo que permite la entrada de un dato de tipo int por teclado
	 * @return int
	 */
	public static int pedirInt(){
		
		int num = 0;
		boolean ok=true;
		
		do {
			try {
				
				ok=true;
				num = entrada.nextInt();

				
			} catch (Exception e) {
				System.out.println("Numero no valido. Por favor, vuelva a introducirlo: ");
				ok=false;
			}
			
			entrada.nextLine();
		}while(ok==false);
		
		
		
		return num;
	}
	
	/**
	 * Metodo que permite la entrada de un dato de tipo int por teclado pero que solo permite que este se encuentre en una determinada horquilla de numeros.
	 * @param min 
	 * @param max
	 * @return int
	 */
	public static int pedirInt_Parametros(int min, int max) {
		
		String frase = "Numero no valido. Por favor, vuelva a introducirlo: ";
		int num = 0;
		boolean ok=true;
		
		do {
			
			do {
				try {
					num = entrada.nextInt();
					ok=true;
					
				} catch (Exception e) {
					System.out.println(frase);
					ok=false;
				}
				entrada.nextLine();
			}while(ok==false);
		
			if (num < min || num > max) {
				
				ok=false;
				System.out.println(frase);
			}
			
		} while(ok==false);
		
		
		
		return num;
	}
	
	/**
	 * Metodo que permite la entrada de un dato de tipo String por teclado
	 * @return String
	 */
	public static String pedirString() {
		
		String cadena = entrada.nextLine();
		
		return cadena;
		
	}
	
	public static LocalDate pedirFecha(int annoInicio, int annoLimite) {
		
		System.out.println("Introduzca un anno:");
		int year = pedirInt_Parametros(annoInicio, annoLimite);
		System.out.println("Introduzca un mes:");
		int month = pedirInt_Parametros(1, 12);
		System.out.println("Introduzca un dia:");
		int dayOfMonth = pedirInt_Parametros(1, 31);
		
		return LocalDate.of(year, month, dayOfMonth);
	}
	
}
