package sistemske_operacije;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import menjacnica.logika.domen.Valuta;

public class SOSacuvajUFile {
	public static void izvrsi(LinkedList<Valuta> kursnaLista, String putanja) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(putanja)));

			out.writeObject(kursnaLista);

			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
}
