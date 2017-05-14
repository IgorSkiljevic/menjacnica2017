package sistemske_operacije;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import menjacnica.logika.domen.Valuta;

public class SOUcitajIzFajla {
	public static LinkedList<Valuta> izvrsi(LinkedList<Valuta> kursnaLista, String putanja) {
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(putanja)));

			kursnaLista = (LinkedList<Valuta>) (in.readObject());

			in.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return kursnaLista;
	}
}
