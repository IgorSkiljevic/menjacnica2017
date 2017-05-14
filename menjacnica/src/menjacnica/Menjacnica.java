package menjacnica;

import java.util.LinkedList;

import sistemske_operacije.SODodajValutu;
import sistemske_operacije.SOIzvrsiTransakciju;
import sistemske_operacije.SOObrisiValutu;
import sistemske_operacije.SOSacuvajUFile;
import sistemske_operacije.SOUcitajIzFajla;

public class Menjacnica implements MenjacnicaInterface {

	private LinkedList<Valuta> kursnaLista = new LinkedList<Valuta>();

	@Override
	public void dodajValutu(Valuta valuta) {
		kursnaLista = SODodajValutu.izvrsi(kursnaLista, valuta);
	}

	@Override
	public void obrisiValutu(Valuta valuta) {
		kursnaLista = SOObrisiValutu.izvrsi(kursnaLista, valuta);
	}

	@Override
	public double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos) {
		return SOIzvrsiTransakciju.izvrsi(valuta, prodaja, iznos);
	}

	@Override
	public LinkedList<Valuta> vratiKursnuListu() {
		return kursnaLista;
	}

	@Override
	public void ucitajIzFajla(String putanja) {
		kursnaLista = SOUcitajIzFajla.izvrsi(kursnaLista, putanja);
	}

	@Override
	public void sacuvajUFajl(String putanja) {
		SOSacuvajUFile.izvrsi(kursnaLista, putanja);
	}

}
