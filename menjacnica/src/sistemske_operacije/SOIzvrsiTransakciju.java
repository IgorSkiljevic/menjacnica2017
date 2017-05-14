package sistemske_operacije;

import menjacnica.logika.domen.Valuta;

public class SOIzvrsiTransakciju {
	public static double izvrsi(Valuta valuta, boolean prodaja, double iznos) {
		if (prodaja)
			return iznos * valuta.getProdajni();
		else
			return iznos * valuta.getKupovni();

	}
}
