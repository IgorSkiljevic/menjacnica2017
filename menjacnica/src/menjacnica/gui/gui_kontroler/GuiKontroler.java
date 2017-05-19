package menjacnica.gui.gui_kontroler;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;
import menjacnica.logika.domen.Menjacnica;
import menjacnica.logika.domen.Valuta;
import menjacnica.logika.interfejsi.MenjacnicaInterface;

public class GuiKontroler {
	private static DodajKursGUI dodajKursGui;
	private static IzvrsiZamenuGUI izvrsiZamenuGui;
	private static MenjacnicaGUI menjacnicaGui;
	private static ObrisiKursGUI obrisiKursGui;
	private static MenjacnicaInterface sistem = new Menjacnica();

	public static void prikaziMenjacnicaGui() {
		menjacnicaGui = new MenjacnicaGUI();
		menjacnicaGui.setVisible(true);

	}

	public static void main(String[] args) {
		prikaziMenjacnicaGui();
	}

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGui, "Da li ZAISTA zelite da izadjete iz apliacije",
				"Izlazak", JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnicaGui);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute();
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void prikaziSveValute() {

		((MenjacnicaTableModel) menjacnicaGui.table.getModel()).staviSveValuteUModel(sistem.vratiKursnuListu());
	}

	public static void otvoriProzorDodajKursGui() {
		dodajKursGui = new DodajKursGUI(menjacnicaGui);
		dodajKursGui.setLocationRelativeTo(menjacnicaGui);
		dodajKursGui.setVisible(true);
		// prikaziSveValute();
	}

	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaGui);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void dodajValutu(int sifra, String naziv, String skraceniNaziv, double prodajni, double kupovni,
			double srednji) {
		Valuta valuta = new Valuta(sifra, skraceniNaziv, skraceniNaziv, kupovni, srednji, prodajni);
		sistem.dodajValutu(valuta);
	}

	public static void prikaziObrisiKursGui() {
		if (menjacnicaGui.table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (menjacnicaGui.table.getModel());

			obrisiKursGui = new ObrisiKursGUI(menjacnicaGui, model.vratiValutu(menjacnicaGui.table.getSelectedRow()));

			obrisiKursGui.setLocationRelativeTo(menjacnicaGui);
			obrisiKursGui.setVisible(true);
		}
	}

	public static void izvrsiZamenu() {

		if (menjacnicaGui.table.getSelectedRow() != -1) {

			MenjacnicaTableModel model = (MenjacnicaTableModel) (menjacnicaGui.table.getModel());
			Valuta v = model.vratiValutu(menjacnicaGui.table.getSelectedRow());

			izvrsiZamenuGui = new IzvrsiZamenuGUI(menjacnicaGui, v);
			izvrsiZamenuGui.setLocationRelativeTo(menjacnicaGui);
			izvrsiZamenuGui.setVisible(true);
			prikaziValutuIzvrsiZamenaGui(v.getProdajni(), v.getKupovni(), v.getSkraceniNaziv());
		}
	}

	public static void zatvoriProzorObrisiKursGui() {
		if (obrisiKursGui != null) {
			obrisiKursGui.dispose();
			obrisiKursGui = null;
		}
	}

	public static void zatvoriProzorIzrsiZamenuGui() {
		if (izvrsiZamenuGui != null) {
			izvrsiZamenuGui.dispose();
			izvrsiZamenuGui = null;
		}
	}

	public static void zatvoriProzorDodajKursGui() {
		if (dodajKursGui != null) {
			dodajKursGui.dispose();
			dodajKursGui = null;
		}
	}

	public static void zameni() {
		try {
			double konacniIznos = GuiKontroler.sistem.izvrsiTransakciju(
					((MenjacnicaTableModel) menjacnicaGui.table.getModel()).vratiValutu(
							menjacnicaGui.table.getSelectedRow()),
					izvrsiZamenuGui.rdbtnProdaja.isSelected(),
					Double.parseDouble(izvrsiZamenuGui.textFieldIznos.getText()));

			izvrsiZamenuGui.textFieldKonacniIznos.setText("" + konacniIznos);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui, e1.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void prikaziAboutProzor() {
		JOptionPane.showMessageDialog(menjacnicaGui, "Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void obrisiValutu(int sifra, String naziv, String skraceniNaziv, double prodajni, double kupovni,
			double srednji) {
		Valuta valuta = new Valuta(sifra, skraceniNaziv, skraceniNaziv, kupovni, srednji, prodajni);
		sistem.obrisiValutu(valuta);
	}

	private static void prikaziValutuIzvrsiZamenaGui(double prodajni, double kupovni, String skraceniNaziv) {
		izvrsiZamenuGui.getTextFieldProdajniKurs().setText("" + prodajni);
		izvrsiZamenuGui.getTextFieldKupovniKurs().setText("" + kupovni);
		izvrsiZamenuGui.getTextFieldValuta().setText(skraceniNaziv);
	}

	public static void unesiKurs() {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(dodajKursGui.getTextFieldNaziv().getText());
			valuta.setSkraceniNaziv(dodajKursGui.getTextFieldSkraceniNaziv().getText());
			valuta.setSifra((Integer) (dodajKursGui.getSpinnerSifra().getValue()));
			valuta.setProdajni(Double.parseDouble(dodajKursGui.getTextFieldProdajniKurs().getText()));
			valuta.setKupovni(Double.parseDouble(dodajKursGui.getTextFieldKupovniKurs().getText()));
			valuta.setSrednji(Double.parseDouble(dodajKursGui.getTextFieldSrednjiKurs().getText()));

			// Dodavanje valute u kursnu listu
			dodajValutu(valuta.getSifra(), valuta.getNaziv(), valuta.getSkraceniNaziv(), valuta.getProdajni(),
					valuta.getKupovni(), valuta.getSrednji());
					// glavniProzor.sistem.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			prikaziSveValute();

			// Zatvaranje DodajValutuGUI prozora
			zatvoriProzorDodajKursGui();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(dodajKursGui.getContentPane(), e1.getMessage(), "Greska",
					JOptionPane.ERROR_MESSAGE);

		}
	}
}
