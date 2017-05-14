package menjacnica.logika.interfejsi;

import java.util.LinkedList;

import menjacnica.logika.domen.Valuta;

public interface MenjacnicaInterface {

	public void dodajValutu(Valuta valuta);

	public void obrisiValutu(Valuta valuta);

	public double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos);

	public LinkedList<Valuta> vratiKursnuListu();

	public void ucitajIzFajla(String putanja);

	public void sacuvajUFajl(String putanja);

}
