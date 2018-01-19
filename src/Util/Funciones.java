package Util;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Funciones {

	private static final double DAY_MILLIS = 1000*60*60 * 24.0015;
	private static final double WEEK_MILLIS = DAY_MILLIS * 7;
	private static final double MONTH_MILLIS = DAY_MILLIS * 30.43675;
	private static final double YEAR_MILLIS = WEEK_MILLIS * 52.2;

	public static int interval( int calUnit, Date d1, Date d2 ) {

		//interval( Calendar.DATE, then, now );
		//interval( Calendar.WEEK_OF_YEAR, then, now );
		//interval( Calendar.MONTH, then, now );
		//interval( Calendar.YEAR, then, now );

		boolean neg = false;
		if( d1.after(d2) ) {
			Date temp = d1;
			d1 = d2;
			d2 = temp;
			neg = true;
		}

		// estimate the diff. d1 is now guaranteed <= d2
		int estimate = (int)getEstDiff( calUnit, d1, d2 );

		// convert the Dates to GregorianCalendars
		GregorianCalendar c1 = new GregorianCalendar();
		c1.setTime(d1);
		GregorianCalendar c2 = new GregorianCalendar();
		c2.setTime(d2);

		// add 2 units less than the estimate to 1st date,
		// then serially add units till we exceed 2nd date
		c1.add( calUnit, (int)estimate - 2 );
		for( int i=estimate-1; ; i++ ) {
			c1.add( calUnit, 1 );
			if( c1.after(c2) )
				return neg ? 1-i : i-1;
		}
	}

	private static int getEstDiff( int calUnit, Date d1, Date d2 ) {
		long diff = d2.getTime() - d1.getTime();
		switch (calUnit) {
			case Calendar.DAY_OF_WEEK_IN_MONTH :
			case Calendar.DATE :
				return (int) (diff / DAY_MILLIS + .5);
			case Calendar.WEEK_OF_YEAR :
				return (int) (diff / WEEK_MILLIS + .5);
			case Calendar.MONTH :
				return (int) (diff / MONTH_MILLIS + .5);
			case Calendar.YEAR :
				return (int) (diff / YEAR_MILLIS + .5);
			default:
				return 0;
		} /* endswitch */
	}

	public static String capital(String string) {
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
				found = false;
			}
		}
		return String.valueOf(chars);
	}

	public static int entero(String conv) {
		try {
			return Integer.parseInt(conv);
		} catch(Exception e) {
			return 0;
		}
	}

	public static double doble(String conv) {
		try {
			return Double.parseDouble(conv);
		} catch(Exception e) {
			return (double)0.0;
		}
	}

	public static float flotante(String conv) {
		try {
			return Float.parseFloat(conv);
		} catch(Exception e) {
			return (float)0.0;
		}
	}

	public static double round(double val, int places) {
		long factor = (long)Math.pow(10,places);
		// Shift the decimal the correct number of places
		// to the right.
		val = val * factor;
		// Round to the nearest integer.
		long tmp = (long)val;	//Math.round(val);
		// Shift the decimal the correct number of places
		// back to the left.
		return (double)tmp / factor;
	}

	public static float round(float val, int places) {
		return (float)round((double)val, places);
	}

	public static String quote( String sValue ) {
		String squote = "'";
		char c;
		char quote=squote.charAt(0);
		String ret = "";
		boolean success=false;
		//ret += quote;

		for( int i = 0; i < sValue.length(); i++ ) {
			c = sValue.charAt( i );
			if( c == quote ) success=true;//ret += "\\";
			ret += c;
		}
		ret = (success?"\"":quote) + ret + (success?"\"":quote);

		return ret;
	}

	public static String fecha() {

		return fecha(new Date());
	}

	public static String horaEnvioToNotificacion(){
		Calendar calendar=Calendar.getInstance();
		DateFormat dateFormat=new SimpleDateFormat("HH:mm");
		return dateFormat.format(calendar.getTime());
	}

	public static String fechaEnvioToNotificacion(){
		Calendar calendar=Calendar.getInstance();
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(calendar.getTime());
	}

	public static int mes() {
		Calendar calendar = Calendar.getInstance();
		return mes(calendar);
	}

	public static int mes(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return mes(calendar);
	}

	public static String getDateFormat(String format){
		Calendar calendar=Calendar.getInstance();
		DateFormat dateFormat=new SimpleDateFormat(format);
		return dateFormat.format(calendar.getTime()).toString();
	}

	public static boolean compareDate(String date,String caracterSeparador){
		boolean res=false;
		String sep[]=date.split(caracterSeparador);
		int mesActual=entero(getDateFormat("MM")),diaActual=entero(getDateFormat("dd")),anioActual=entero(getDateFormat("yyyy")),diaP,mesP,anioP;
		diaP=entero(sep[2]);
		mesP=entero(sep[1]);
		anioP=entero(sep[0]);
		if(compareTo(anioActual,anioP) && compareTo(diaActual,diaP) && compareTo(mesActual,mesP)){
			res=true;
		}
		return res;
	}

	public static boolean mayorIgualHour(String hour){
		boolean res=false;
		int horaActual=entero(getDateFormat("HH")),minutosActual=entero(getDateFormat("mm")),minutosP,horaP;String sep[];
		sep=hour.split(":");
		horaP=entero(sep[0]);minutosP=entero(sep[1]);
		if(horaActual>horaP){
			res=true;
		}else if(horaActual==horaP){
			if(minutosActual>=minutosP){
				res=true;
			}
		}
		return res;
	}

	public static boolean compareTo(int num1,int num2){
		return num1==num2;
	}

	public static String mesActual(){
		Calendar calendar=Calendar.getInstance();
		DateFormat dateFormat=new SimpleDateFormat("dd");
		return dateFormat.format(calendar.getTime()).toString();
	}

	public static int mes(Calendar calendar) {
		if(calendar==null) return 0;
		else return calendar.get(Calendar.MONTH);
	}

	public static int hora() {
		Calendar calendar = Calendar.getInstance();
		return hora(calendar);
	}

	public static int hora(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return hora(calendar);
	}

	public static int hora(Calendar calendar) {
		if(calendar==null) return 0;
		else return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int minutos() {
		Calendar calendar = Calendar.getInstance();
		return minutos(calendar);
	}

	public static int minutos(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return dia(calendar);
	}

	public static int minutos(Calendar calendar) {
		if(calendar==null) return 0;

		else return calendar.get(Calendar.MINUTE);
	}

	public static int dia() {
		Calendar calendar = Calendar.getInstance();
		return dia(calendar);
	}

	public static void escribirEnConsola(String des,String msg,String tipo){
		if(tipo.equals("yes")){
			System.out.println(des+":   "+msg);
		}else if(tipo.equals("err")){
			System.err.println(des+":   "+msg);
		}

	}

	public static int dia(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return dia(calendar);
	}

	public static int dia(Calendar calendar) {
		if(calendar==null) return 0;
		else return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Regresa un entero indicando el dia de la semana
	 * Domingo=0
	 *
	 * @return
	 */

	public static int diaSemana() {
		Calendar calendar = Calendar.getInstance();
		return diaSemana(calendar);
	}

	/**
	 * Regresa un entero indicando el dia de la semana
	 * Domingo=0
	 *
	 * @return
	 */

	public static int diaSemana(String txt) {
		return diaSemana(textoFecha(txt));
	}

	/**
	 * Regresa un entero indicando el dia de la semana
	 * Domingo=0
	 *
	 * @return
	 */

	public static int diaSemana(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);
		return diaSemana(calendar);
	}

	/**
	 * Regresa un entero indicando el dia de la semana
	 * Domingo=0
	 *
	 * @return
	 */

	public static int diaSemana(Calendar calendar) {
		if(calendar==null) return 0;
		else return calendar.get(Calendar.DAY_OF_WEEK)-1;
	}


	public static int anio() {
		return anio(new Date());
	}

	public static int anio(Date fecha) {
		if(fecha==null) return 0;
		else {
			int year=fecha.getYear();
			if (year<1000){year+=1900;}
			return year;
		}
	}

	public static String fecha( Date fecha ) {
		int year=fecha.getYear();
		if (year<1000){year+=1900;}
		int day=fecha.getDay();
		int month=fecha.getMonth();
		int daym=fecha.getDate();

		String dias[]={	"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
		String meses[]={	"Enero","Febrero","Marzo","Abril","Mayo","Junio",
				"Julio","Agosto","Septiembre","Octubre","Noviembre",
				"Diciembre"};

		return dias[day]+" "+daym+" de "+meses[month]+" de "+year+"";
	}

	public static String fecha2( Date fecha ) {
		int year=fecha.getYear();
		if (year<1000){year+=1900;}
		int day=fecha.getDay();
		int month=fecha.getMonth();
		int daym=fecha.getDate();

		String dias[]={	"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
		String meses[]={	"Enero","Febrero","Marzo","Abril","Mayo","Junio",
				"Julio","Agosto","Septiembre","Octubre","Noviembre",
				"Diciembre"};

		return daym+" de "+meses[month]+" del "+year+"";
	}

	public static String mesletra( Date fecha ) {
		int month=fecha.getMonth();

		String meses[]={	"Enero","Febrero","Marzo","Abril","Mayo","Junio",
				"Julio","Agosto","Septiembre","Octubre","Noviembre",
				"Diciembre"};

		return meses[month];
	}


	public static String fechatexto() {
		String ret = anio()+"-"+padl(mes(),"0",2)+"-"+padl(dia(),"0",2);
		return ret;
	}

	public static String fechatexto(Date fecha) {
		try {
			return anio(fecha)+"-"+padl(mes(fecha),"0",2)+"-"+padl(dia(fecha),"0",2);
		} catch(Exception e) {
			return "";
		}
	}

	public static String horatexto() {
		String ret = padl(hora(),"0",2)+":"+padl(minutos(),"0",2);
		return ret;
	}

	public static String horatexto(Date fecha) {
		try {
			return padl(hora(fecha),"0",2)+":"+padl(minutos(fecha),"0",2);
		} catch(Exception e) {
			return "";
		}
	}

	public long getTime() {
		Date fecha = new Date();
		return fecha.getTime();
	}

	static public byte[] convertir(String dato) {
		char []data=dato.toCharArray();
		char aux;
		byte [] convertido = new byte[data.length];
		for(int i=0; i<data.length; i++){ convertido[i]=(byte)(data[i]); }
		return convertido;
	}

	/**
	 * Decodes a BASE-64 encoded stream to recover the original
	 * data. White space before and after will be trimmed away,
	 * but no other manipulation of the input will be performed.
	 *
	 * As of version 1.2 this method will properly handle input
	 * containing junk characters (newlines and the like) rather
	 * than throwing an error. It does this by pre-parsing the
	 * input and generating from that a count of VALID input
	 * characters.
	 **/

	static public byte[] decode(char[] data) throws Exception {

		// as our input could contain non-BASE64 data (newlines,
		// whitespace of any sort, whatever) we must first adjust
		// our count of USABLE data so that...
		// (a) we don't misallocate the output array, and
		// (b) think that we miscalculated our data length
		//     just because of extraneous throw-away junk

		int tempLen = data.length;
		for( int ix=0; ix<data.length; ix++ ) {
			if( (data[ix] > 255) || codes[ data[ix] ] < 0 ) --tempLen;  // ignore non-valid chars and padding
		}

		// calculate required length:
		//  -- 3 bytes for every 4 valid base64 chars
		//  -- plus 2 bytes if there are 3 extra base64 chars,
		//     or plus 1 byte if there are 2 extra.

		int len = (tempLen / 4) * 3;
		if ((tempLen % 4) == 3) len += 2;
		if ((tempLen % 4) == 2) len += 1;

		byte[] out = new byte[len];

		int shift = 0;   // # of excess bits stored in accum
		int accum = 0;   // excess bits
		int index = 0;

		// we now go through the entire array (NOT using the 'tempLen' value)
		for (int ix=0; ix<data.length; ix++) {
			int value = (data[ix]>255)? -1: codes[ data[ix] ];

			if ( value >= 0 ) {			// skip over non-code
				accum <<= 6;            // bits shift up by 6 each time thru
				shift += 6;             // loop, with new bits being put in
				accum |= value;         // at the bottom.
				if ( shift >= 8 ) { 	// whenever there are 8 or more shifted in,
					shift -= 8;         // write them out (from the top, leaving any
					out[index++] =      // excess at the bottom for next iteration.
							(byte) ((accum >> shift) & 0xff);
				}
			}

			// we will also have skipped processing a padding null byte ('=') here;
			// these are used ONLY for padding to an even length and do not legally
			// occur as encoded data. for this reason we can ignore the fact that
			// no index++ operation occurs in that special case: the out[] array is
			// initialized to all-zero bytes to start with and that works to our
			// advantage in this combination.
		}

		// if there is STILL something wrong we just have to throw up now!
		if( index != out.length) {
			throw new Exception("Miscalculated data length (wrote " + index + " instead of " + out.length + ")");
		}

		return out;
	}


	//
	// code characters for values 0..63
	//

	static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

	//
	// lookup table for converting base64 characters to value in range 0..63
	//

	static private byte[] codes = new byte[256];

	static {
		for (int i=0; i<256; i++) codes[i] = -1;
		for (int i = 'A'; i <= 'Z'; i++) codes[i] = (byte)(     i - 'A');
		for (int i = 'a'; i <= 'z'; i++) codes[i] = (byte)(26 + i - 'a');
		for (int i = '0'; i <= '9'; i++) codes[i] = (byte)(52 + i - '0');
		codes['+'] = 62;
		codes['/'] = 63;
	}

	/******************************************************/
	/**
	 * returns an array of base64-encoded characters to represent the
	 * passed data array.
	 *
	 * @param data the array of bytes to encode
	 * @return base64-coded character array.
	 */

	static public char[] encode(byte[] data) {

		char[] out = new char[((data.length + 2) / 3) * 4];

		//
		// 3 bytes encode to 4 chars.  Output is always an even
		// multiple of 4 characters.
		//

		for (int i=0, index=0; i<data.length; i+=3, index+=4) {
			boolean quad = false;
			boolean trip = false;

			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i+1) < data.length) {
				val |= (0xFF & (int) data[i+1]);
				trip = true;
			}
			val <<= 8;
			if ((i+2) < data.length) {
				val |= (0xFF & (int) data[i+2]);
				quad = true;
			}
			out[index+3] = alphabet[(quad? (val & 0x3F): 64)];
			val >>= 6;
			out[index+2] = alphabet[(trip? (val & 0x3F): 64)];
			val >>= 6;
			out[index+1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index+0] = alphabet[val & 0x3F];
		}
		return out;
	}

	public static String padl( int numero,String caracter,int longitud) {
		return padl(numero+"",caracter,longitud);
	}
	public static String padl( long numero,String caracter,int longitud) {
		return padl(numero+"",caracter,longitud);
	}
	public static String padl( String numero,String caracter,int longitud) {
		String retValue = numero;
		for(int i=1;i<longitud;i++) retValue=caracter+retValue;
		return retValue.substring(retValue.length()-longitud,retValue.length());
	}

	/**
	 *Convierte un numero entero a flotante
	 */

	public float convierteCalif(Float calif){
		String cad=calif.toString();
		float retorno=0;
		if(cad.length()==1){
			cad=cad+".0";
		}
		retorno=Float.parseFloat(cad);
		return retorno;
	}
	/**
	 *Funcion que regresa una cadena rellenada con el caracter y la longitud que se le indique
	 */

	public static String rellenaCad(String Cadena,String caracter,int longitud) {
		String retValue =Cadena;
		for(int i=Cadena.length();i<longitud;i++)
			retValue=caracter+retValue;
		return retValue;
	}
	public static String rellenaCadDerecha(String Cadena,String caracter,int longitud) {
		String retValue =Cadena;
		for(int i=Cadena.length();i<longitud;i++)
			retValue=retValue+caracter;
		return retValue;
	}

	/**
	 * Convierte numero a letra
	 */

	public static String letra(int numero) {
		return letra(numero,"");
	}

	public static String letra(int numero,String genero) {

		int longitud=0, i, bw=0;
		int unidad, decena, centena, millar, deceMillar, centeMillar, millon, deceMillon;
		int centeMillon, millarMillon, deceMillarMillon, centeMillarMillon, billon;
		String numTexto="", num, nom="";


		num=numero+"";
		longitud=num.length();
		if(longitud > 7) return "Numero excedido";
		if(longitud == 7) bw = 1;
		if(longitud < 7) {
			bw = 1;
			for(i=1;i<=(7-longitud);i++) {
				nom=('0') + nom;
			}
			num=nom+num;
		}

		unidad = num.charAt(6);
		decena = num.charAt(5);
		centena = num.charAt(4);
		millar = num.charAt(3);
		deceMillar = num.charAt(2);
		centeMillar = num.charAt(1);
		millon = num.charAt(0);


		if (millon != '0') {
			switch (millon) {
				case '1':
					numTexto = numTexto + (" Un millones");
					break;
				case '2':
					numTexto = numTexto + (" Dos millones");
					break;
				case '3':
					numTexto = numTexto + (" Tres millones");
					break;
				case '4':
					numTexto = numTexto + (" Cuatro millones");
					break;
				case '5':
					numTexto = numTexto + (" Cinco millones");
					break;
				case '6':
					numTexto = numTexto + (" Seis millones");
					break;
				case '7':
					numTexto = numTexto + (" Siete millones");
					break;
				case '8':
					numTexto = numTexto + (" ocho millones");
					break;
				case '9':
					numTexto = numTexto + (" Nueve millones");
					break;
			};
		}


		if (centeMillar != '0') {
			switch (centeMillar) {
				case '1':
					numTexto = numTexto + (" Cien");
					if (deceMillar != '0') numTexto = numTexto + ("to");
					else numTexto = numTexto + ("mil");
					break;
				case '2':
					numTexto = numTexto + (" Doscientos");
					if (deceMillar != '0') numTexto = numTexto + ("");
					else numTexto = numTexto + ("mil");
					break;
				case '3':
					numTexto = numTexto + (" Trescientos");
					if (deceMillar != '0') numTexto = numTexto + ("");
					else numTexto = numTexto + ("mil");
					break;
				case '4':
					numTexto = numTexto + (" Cuatrocientos");
					if (deceMillar != '0') numTexto = numTexto + ("");
					else numTexto = numTexto + ("mil");
					break;
				case '5':
					numTexto = numTexto + (" Quinientos");
					if (deceMillar != '0') numTexto = numTexto + ("");
					else numTexto = numTexto + ("mil");
					break;
				case '6':
					numTexto = numTexto + (" Seiscientos");
					if (deceMillar != '0') numTexto = numTexto + ("");
					else numTexto = numTexto + ("mil");
					break;
				case '7':
					numTexto = numTexto + (" Setecientos");
					if (deceMillar != '0') numTexto = numTexto + ("");
					else numTexto = numTexto + ("mil");
					break;
				case '8':
					numTexto = numTexto + (" Ochocientos");
					if (deceMillar != '0') numTexto = numTexto + ("");
					else numTexto = numTexto + ("mil");
					break;
				case '9':
					numTexto = numTexto + (" Novecientos");
					if (deceMillar != '0') numTexto = numTexto + ("");
					else numTexto = numTexto + ("mil");
					break;
			};
		}

		if (deceMillar != '0') {
			switch(deceMillar) {
				case '1':
					if (millar == '0') numTexto = numTexto + (" Diez mil");
					if (millar == '1') {
						numTexto = numTexto + (" Once mil");
						millar = '0';
					}
					if (millar == '2') {
						numTexto = numTexto + (" Doce mil");
						millar = '0';
					}
					if (millar == '3') {
						numTexto = numTexto + (" Trece mil");
						millar = '0';
					}
					if (millar == '4') {
						numTexto = numTexto + (" Catorce mil");
						millar = '0';
					}
					if (millar == '5') {
						numTexto = numTexto + (" Quince mil");
						millar = '0';
					}
					if (millar != '0' && millar != '1' && millar != '2' && millar != '3' && millar != '4' && millar != '5') numTexto = numTexto + (" Dieci");
					break;
				case '2':
					numTexto = numTexto + (" Veint");
					if (millar != '0') numTexto = numTexto +("i");
					else numTexto = numTexto +("e mil");
					break;
				case '3':
					numTexto = numTexto + (" Treint");
					if (millar != '0') numTexto = numTexto +("a y ");
					else numTexto = numTexto +("a mil");
					break;
				case '4':
					numTexto = numTexto + (" Cuarent");
					if (millar != '0') numTexto = numTexto +("a y ");
					else numTexto = numTexto +("a mil");
					break;
				case '5':
					numTexto = numTexto + (" Cincuent");
					if (millar != '0') numTexto = numTexto +("a y ");
					else numTexto = numTexto +("a mil");
					break;
				case '6':
					numTexto = numTexto + (" Sesent");
					if (millar != '0') numTexto = numTexto +("a y ");
					else numTexto = numTexto +("a mil");
					break;
				case '7':
					numTexto = numTexto + (" Setent");
					if (millar != '0') numTexto = numTexto +("a y ");
					else numTexto = numTexto +("a mil");
					break;
				case '8':
					numTexto = numTexto + (" Ochent");
					if (millar != '0') numTexto = numTexto +("a y ");
					else numTexto = numTexto +("a mil");
					break;
				case '9':
					numTexto = numTexto + (" Novent");
					if (millar != '0') numTexto = numTexto +("a y ");
					else numTexto = numTexto +("a mil");
					break;
			};
		}

		if(millar != '0') {
			switch(millar) {
				case '1':
					numTexto = numTexto + ("un mil ");
					break;
				case '2':
					numTexto = numTexto + (" dos mil ");
					break;
				case '3':
					numTexto = numTexto + (" tres mil ");
					break;
				case '4':
					numTexto = numTexto + (" cuatro mil ");
					break;
				case '5':
					numTexto = numTexto + (" cinco mil ");
					break;
				case '6':
					numTexto = numTexto + ("seis mil ");
					break;
				case '7':
					numTexto = numTexto + ("siete mil ");
					break;
				case '8':
					numTexto = numTexto + ("ocho mil ");
					break;
				case '9':
					numTexto = numTexto + ("nueve mil ");
					break;
			};
		}

		if(centena != '0') {
			switch(centena) {
				case '1':
					numTexto = numTexto + (" Ciento ");
					break;
				case '2':
					numTexto = numTexto + (" Doscientos ");
					break;
				case '3':
					numTexto = numTexto + (" Trescientos ");
					break;
				case '4':
					numTexto = numTexto + (" Cuatrocientos ");
					break;
				case '5':
					numTexto = numTexto + (" Quinientos ");
					break;
				case '6':
					numTexto = numTexto + (" Seiscientos ");
					break;
				case '7':
					numTexto = numTexto + (" Setecientos ");
					break;
				case '8':
					numTexto = numTexto + (" Ochocientos ");
					break;
				case '9':
					numTexto = numTexto + (" Novecientos ");
					break;
			};
		}

		if (decena != '0') {
			switch (decena) {
				case '1':
					if (unidad == '0') numTexto = numTexto + (" Diez");
					if (unidad == '1') {
						numTexto = numTexto + (" Once");
						unidad = '0';
					}
					if (unidad == '2') {
						numTexto = numTexto + (" Doce");
						unidad = '0';
					}
					if (unidad == '3') {
						numTexto = numTexto + (" Trece");
						unidad = '0';
					}
					if (unidad == '4') {
						numTexto = numTexto + (" Catorce");
						unidad = '0';
					}
					if (unidad == '5') {
						numTexto = numTexto + (" Quince");
						unidad = '0';
					}
					if (unidad != '0' && unidad != '1' && unidad != '2' && unidad != '3' && unidad != '4' && unidad != '5' ) numTexto = numTexto + (" Dieci");
					break;
				case '2':
					numTexto = numTexto + (" Veint");
					if (unidad != '0') numTexto = numTexto + ("i");
					else numTexto = numTexto + ("e");
					break;
				case '3':
					numTexto = numTexto + (" Treint");
					if (unidad != '0') numTexto = numTexto + ("a y ");
					else numTexto = numTexto + ("a");
					break;
				case '4':
					numTexto = numTexto + (" Cuarent");
					if (unidad != '0') numTexto = numTexto + ("a y ");
					else numTexto = numTexto + ("a");
					break;
				case '5':
					numTexto = numTexto + (" Cincuent");
					if (unidad != '0') numTexto = numTexto + ("a y ");
					else numTexto = numTexto + ("a");
					break;
				case '6':
					numTexto = numTexto + (" Sesent");
					if (unidad != '0') numTexto = numTexto + ("a y ");
					else numTexto = numTexto + ("a");
					break;
				case '7':
					numTexto = numTexto + (" Setent");
					if (unidad != '0') numTexto = numTexto + ("a y ");
					else numTexto = numTexto + ("a");
					break;
				case '8':
					numTexto = numTexto + (" Ochent");
					if (unidad != '0') numTexto = numTexto + ("a y ");
					else numTexto = numTexto + ("a");
					break;
				case '9':
					numTexto = numTexto + (" Novent");
					if (unidad != '0') numTexto = numTexto + ("a y ");
					else numTexto = numTexto + ("a");
					break;
			};
		}

		if (unidad != '0') {
			switch (unidad) {
				case '1':
					numTexto = numTexto + (genero.equals("F")?"una":"un");
					break;
				case '2':
					numTexto = numTexto + ("dos");
					break;
				case '3':
					numTexto = numTexto + ("tres");
					break;
				case '4':
					numTexto = numTexto + ("cuatro");
					break;
				case '5':
					numTexto = numTexto + ("cinco");
					break;
				case '6':
					numTexto = numTexto + ("seis");
					break;
				case '7':
					numTexto = numTexto + ("siete");
					break;
				case '8':
					numTexto = numTexto + ("ocho");
					break;
				case '9':
					numTexto = numTexto + ("nueve");
					break;
			};
		}

		numTexto = numTexto.trim();
		numTexto = numTexto.equals("")?"cero":numTexto;
		return numTexto.toUpperCase();
	}


	/**
	 * Provides a method to encode any string into a URL-safe
	 * form.
	 * Non-ASCII characters are first encoded as sequences of
	 * two or three bytes, using the UTF-8 algorithm, before being
	 * encoded as %HH escapes.
	 *
	 * Created: 17 April 1997
	 * Author: Bert Bos <bert@w3.org>
	 *
	 * URLUTF8Encoder: http://www.w3.org/International/URLUTF8Encoder.java
	 *
	 * Copyright  1997 World Wide Web Consortium, (Massachusetts
	 * Institute of Technology, European Research Consortium for
	 * Informatics and Mathematics, Keio University). All Rights Reserved.
	 * This work is distributed under the W3C Software License [1] in the
	 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
	 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
	 * PURPOSE.
	 *
	 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
	 */


	final static String[] hex = {
			"%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07",
			"%08", "%09", "%0a", "%0b", "%0c", "%0d", "%0e", "%0f",
			"%10", "%11", "%12", "%13", "%14", "%15", "%16", "%17",
			"%18", "%19", "%1a", "%1b", "%1c", "%1d", "%1e", "%1f",
			"%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27",
			"%28", "%29", "%2a", "%2b", "%2c", "%2d", "%2e", "%2f",
			"%30", "%31", "%32", "%33", "%34", "%35", "%36", "%37",
			"%38", "%39", "%3a", "%3b", "%3c", "%3d", "%3e", "%3f",
			"%40", "%41", "%42", "%43", "%44", "%45", "%46", "%47",
			"%48", "%49", "%4a", "%4b", "%4c", "%4d", "%4e", "%4f",
			"%50", "%51", "%52", "%53", "%54", "%55", "%56", "%57",
			"%58", "%59", "%5a", "%5b", "%5c", "%5d", "%5e", "%5f",
			"%60", "%61", "%62", "%63", "%64", "%65", "%66", "%67",
			"%68", "%69", "%6a", "%6b", "%6c", "%6d", "%6e", "%6f",
			"%70", "%71", "%72", "%73", "%74", "%75", "%76", "%77",
			"%78", "%79", "%7a", "%7b", "%7c", "%7d", "%7e", "%7f",
			"%80", "%81", "%82", "%83", "%84", "%85", "%86", "%87",
			"%88", "%89", "%8a", "%8b", "%8c", "%8d", "%8e", "%8f",
			"%90", "%91", "%92", "%93", "%94", "%95", "%96", "%97",
			"%98", "%99", "%9a", "%9b", "%9c", "%9d", "%9e", "%9f",
			"%a0", "%a1", "%a2", "%a3", "%a4", "%a5", "%a6", "%a7",
			"%a8", "%a9", "%aa", "%ab", "%ac", "%ad", "%ae", "%af",
			"%b0", "%b1", "%b2", "%b3", "%b4", "%b5", "%b6", "%b7",
			"%b8", "%b9", "%ba", "%bb", "%bc", "%bd", "%be", "%bf",
			"%c0", "%c1", "%c2", "%c3", "%c4", "%c5", "%c6", "%c7",
			"%c8", "%c9", "%ca", "%cb", "%cc", "%cd", "%ce", "%cf",
			"%d0", "%d1", "%d2", "%d3", "%d4", "%d5", "%d6", "%d7",
			"%d8", "%d9", "%da", "%db", "%dc", "%dd", "%de", "%df",
			"%e0", "%e1", "%e2", "%e3", "%e4", "%e5", "%e6", "%e7",
			"%e8", "%e9", "%ea", "%eb", "%ec", "%ed", "%ee", "%ef",
			"%f0", "%f1", "%f2", "%f3", "%f4", "%f5", "%f6", "%f7",
			"%f8", "%f9", "%fa", "%fb", "%fc", "%fd", "%fe", "%ff"
	};

	/**
	 * Encode a string to the "x-www-form-urlencoded" form, enhanced
	 * with the UTF-8-in-URL proposal. This is what happens:
	 *
	 * <ul>
	 * <li><p>The ASCII characters 'a' through 'z', 'A' through 'Z',
	 *        and '0' through '9' remain the same.
	 *
	 * <li><p>The unreserved characters - _ . ! ~ * ' ( ) remain the same.
	 *
	 * <li><p>The space character ' ' is converted into a plus sign '+'.
	 *
	 * <li><p>All other ASCII characters are converted into the
	 *        3-character string "%xy", where xy is
	 *        the two-digit hexadecimal representation of the character
	 *        code
	 *
	 * <li><p>All non-ASCII characters are encoded in two steps: first
	 *        to a sequence of 2 or 3 bytes, using the UTF-8 algorithm;
	 *        secondly each of these bytes is encoded as "%xx".
	 * </ul>
	 *
	 * @param s The string to be encoded
	 * @return The encoded string
	 */
	public static String encode(String s)
	{
		StringBuffer sbuf = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			int ch = s.charAt(i);
			if ('A' <= ch && ch <= 'Z') {		// 'A'..'Z'
				sbuf.append((char)ch);
			} else if ('a' <= ch && ch <= 'z') {	// 'a'..'z'
				sbuf.append((char)ch);
			} else if ('0' <= ch && ch <= '9') {	// '0'..'9'
				sbuf.append((char)ch);
			} else if (ch == '-' || ch == '_'		// unreserved
					|| ch == '.' || ch == '!'
					|| ch == '~' || ch == '*'
					|| ch == '\'' || ch == '('
					|| ch == ')') {
				sbuf.append((char)ch);
			} else if (ch <= 0x007f) {		// other ASCII
				sbuf.append(hex[ch]);
			} else if (ch <= 0x07FF) {		// non-ASCII <= 0x7FF
				sbuf.append(hex[0xc0 | (ch >> 6)]);
				sbuf.append(hex[0x80 | (ch & 0x3F)]);
			} else {					// 0x7FF < ch <= 0xFFFF
				sbuf.append(hex[0xe0 | (ch >> 12)]);
				sbuf.append(hex[0x80 | ((ch >> 6) & 0x3F)]);
				sbuf.append(hex[0x80 | (ch & 0x3F)]);
			}
		}
		return sbuf.toString();
	}

	public static String URLEncoder(String s) {
		return URLEncoder.encode(s);
		//return URLEncoder.encode(s, "UTF-8");
	}

	public static String URLDecoder(String s) {
		return URLDecoder.decode(s);
	}

	/**
	 *Funcion que regresa la quincena actual
	 */
	public static String getQuinActual () {
		int ad;
		String quin ="";
		if (dia()>15)
			ad =mes()*2;
		else
			ad =mes()*2-1;
		if(ad<10)
			quin =anio()+"0"+ad;
		else
			quin =anio()+""+ad;

		return quin;
	}

	public static String getQuinActual(Date fecha) { //fechaToQuin(Date fecha){
		int ad;
		String quin ="";
		if (dia()>15)
			ad =mes(fecha)*2;
		else
			ad =mes(fecha)*2-1;
		if(ad<10)
			quin =anio(fecha)+"0"+ad;
		else
			quin =anio(fecha)+""+ad;

		return quin;
	}

	/**
	 *Remplaza caracteres especiales
	 */

	public static String unescape(String s) {
		String r = s;
		int n=0;
		ArrayList cs = new ArrayList();
		cs.add("�|�|1".split("\\|"));
		cs.add("�|�|0".split("\\|"));
		cs.add("<|&lt;|3".split("\\|"));
		cs.add("�|??|1".split("\\|"));
		cs.add("<br>|\n|0".split("\\|"));
		for(int i=0;i<cs.size();i++) {
			String[] c = (String[])cs.get(i);
			n=0;
			while(n>=0) {
				n=r.indexOf(c[1]);
				if(n>=0) {
					r = r.substring(0,n) + c[0]+  r.substring(n+Funciones.entero(c[2])+1,r.length());
					//System.out.println(r);
				}
			}
		}
		return r;
	}

	public static Date textoFecha(String txt) {
		String[] p = txt.split("\\-");
		try {
			int anio = entero(p[0]);
			int mes = entero(p[1]);
			int dia = entero(p[2]);
			return fecha( anio,mes,dia);
		} catch(Exception e) {
			return null;
		}

	}

	public static Date fecha(int anio,int mes, int dia) {
		Calendar calendar = new GregorianCalendar(anio,mes-1,dia);
		return calendar.getTime();
	}

	public static ArrayList anios(int ini,int fin) {
		ArrayList ret = new ArrayList();
		for(int i=fin;i>=ini;i--) {
			ret.add( new OpcionBean(i+"",i+"") );
		}
		return ret;
	}

	public static ArrayList qnas() {
		ArrayList ret = new ArrayList();
		for(int i=1;i<25;i++) {
			ret.add( new OpcionBean(padl(i,"0",2),padl(i,"0",2)) );
		}
		return ret;
	}

	public static ArrayList listaAnios(int ini,int fin,boolean primerovacio) throws Exception {
		ArrayList ret = new ArrayList();
		OpcionBean ob = null;
		if(primerovacio) {
			ob = new OpcionBean("","");
			ret.add(ob);
		}
		for(int i=ini;i<=fin;i++) {
			ob = new OpcionBean(i,i);
			ret.add(ob);
		}
		return ret;
	}

	public static ArrayList listaAniosDesc(int ini,int fin,boolean primerovacio) throws Exception {
		ArrayList ret = new ArrayList();
		OpcionBean ob = null;
		if(primerovacio) {
			ob = new OpcionBean("","");
			ret.add(ob);
		}
		for(int i=ini;i>fin;i--) {
			ob = new OpcionBean(i,i);
			ret.add(ob);
		}
		return ret;
	}

	public static ArrayList listaDias(boolean primerovacio) throws Exception {
		ArrayList ret = new ArrayList();
		OpcionBean ob = null;
		if(primerovacio) {
			ob = new OpcionBean("","");
			ret.add(ob);
		}
		for(int i=1;i<=31;i++) {
			ob = new OpcionBean(i,i);
			ret.add(ob);
		}
		return ret;
	}

	public static ArrayList listaMeses(boolean primerovacio) throws Exception {
		ArrayList ret = new ArrayList();
		OpcionBean ob = null;
		if(primerovacio) {
			ob = new OpcionBean("","");
			ret.add(ob);
		}
		ob = new OpcionBean("1","ENERO"); ret.add(ob);
		ob = new OpcionBean("2","FEBRERO"); ret.add(ob);
		ob = new OpcionBean("3","MARZO"); ret.add(ob);
		ob = new OpcionBean("4","ABRIL"); ret.add(ob);
		ob = new OpcionBean("5","MAYO"); ret.add(ob);
		ob = new OpcionBean("6","JUNIO"); ret.add(ob);
		ob = new OpcionBean("7","JULIO"); ret.add(ob);
		ob = new OpcionBean("8","AGOSTO"); ret.add(ob);
		ob = new OpcionBean("9","SEPTIEMBRE"); ret.add(ob);
		ob = new OpcionBean("10","OCTUBRE"); ret.add(ob);
		ob = new OpcionBean("11","NOVIEMBRE"); ret.add(ob);
		ob = new OpcionBean("12","DICIEMBRE"); ret.add(ob);
		return ret;
	}
	public static int intervalo(Date f1, Date f2) {
		long from = new GregorianCalendar(anio(f1),mes(f1), dia(f1)).getTime().getTime();
		long to = new GregorianCalendar(anio(f2),mes(f2), dia(f2)).getTime().getTime();
		double difference = to - from;
		int days = (int)Math.round((difference/(1000*60*60*24)));
		return days;
	}

	public static String decimal(double numero,Integer lon,Integer dec) {
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setMaximumFractionDigits(dec);
		nf.setMinimumFractionDigits(dec);
		String ret = nf.format(numero);
		return Funciones.padl(ret,"0",4);
	}

	public static String romano(int n) {
		Hashtable equiv = new Hashtable();
		equiv.put(1,"I");
		equiv.put(2,"II");
		equiv.put(3,"III");
		equiv.put(4,"IV");
		equiv.put(5,"V");
		equiv.put(6,"VI");
		equiv.put(7,"VII");
		equiv.put(8,"VII");
		equiv.put(9,"IX");
		equiv.put(10,"X");
		String ret = (String)equiv.get(n);
		equiv=null;
		return ret;
	}

	public static ArrayList lstActCanc(boolean primerovacio) throws Exception {
		ArrayList ret = new ArrayList();
		OpcionBean ob = null;
		if(primerovacio) {
			ob = new OpcionBean("","");
			ret.add(ob);
		}
		ob = new OpcionBean("A","ACTIVO"); ret.add(ob);
		ob = new OpcionBean("C","CANCELADO"); ret.add(ob);
		return ret;
	}
	public static ArrayList lstActInac(boolean primerovacio) throws Exception {
		ArrayList ret = new ArrayList();
		OpcionBean ob = null;
		if(primerovacio) {
			ob = new OpcionBean("","");
			ret.add(ob);
		}
		ob = new OpcionBean("A","Activo"); ret.add(ob);
		ob = new OpcionBean("I","Inactivo"); ret.add(ob);
		return ret;
	}

	public static byte[] toByte(Object obj) throws Exception {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream (bs);
		os.writeObject(obj);
		os.close(); os = null;
		return bs.toByteArray();
	}

	public static Object toObject(byte[] bytea) throws Exception {
		ByteArrayInputStream bs = new ByteArrayInputStream(bytea);
		ObjectInputStream is = new ObjectInputStream(bs);
		Object obj = (Object)is.readObject();
		is.close(); is=null;
		bs=null;
		return obj;
	}

	/**
	 * Regresa una lista de Si/No
	 *
	 * Julio Meza 2011-04-03
	 */
	public static ArrayList lstSiNo(boolean primerovacio) throws Exception {
		ArrayList ret = new ArrayList();
		OpcionBean ob = null;
		if(primerovacio) {
			ob = new OpcionBean("","");
			ret.add(ob);
		}
		ob = new OpcionBean("1","Si"); ret.add(ob);
		ob = new OpcionBean("0","No"); ret.add(ob);
		return ret;
	}

	public static boolean esNumero(String cadena){
		boolean valido = true;
		char aux;
		for(int x=0;x<cadena.length();x++){
			aux = cadena.charAt(x);
			valido = Character.isDigit(aux);
			if(!valido) return valido;
		}
		return valido;
	}
	public static String esimporteLetra(Double imp){
		String ret = "";
		//System.out.println("imp "+imp);
		Integer numeroint = (Integer)imp.intValue();
		Double numerocomp = (imp - numeroint.doubleValue())*100;
		Integer numerodec = (Integer)numerocomp.intValue();
		ret = letra(numeroint) +" PESOS "+ padl(numerodec,"0",2)+"/100 M.N.";
		//System.out.println("ret "+ret);
		return ret;
	}


	// convert from UTF-8 -> internal Java String format
	public static String fromUTF8(String s) {
		String out = null;
		try {
			out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return out;
	}

	// convert from internal Java String format -> UTF-8
	public static String toUTF8(String s) {
		String out = null;
		try {
			out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return out;
	}

	public static String left(String cadena,int len) {
		if(cadena.length()<=len) return cadena;
		else {
			return cadena.substring(0,len);
		}
	}

	public static String cardinal(int grado) {
		String[] msg = {
				"Primer","Segundo","Tercero","Cuarto","Quinto","Sexto","Septimo","Octavo","Noveno","Decimo","Undecimo","Doceavo"
		};
		return msg[grado-1];
	}
}
