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

public class GuiKontroler {
	public static DodajKursGUI dodajKursGui;
	public static IzvrsiZamenuGUI izvrsiZamenuGui;
	public static MenjacnicaGUI menjacnicaGui;
	public static ObrisiKursGUI obrisiKursGui;
	public static Menjacnica sistem = new Menjacnica();

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

	public static void dodajValutu(Valuta valuta) {
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
			izvrsiZamenuGui = new IzvrsiZamenuGUI(menjacnicaGui,
					model.vratiValutu(menjacnicaGui.table.getSelectedRow()));

			izvrsiZamenuGui.setLocationRelativeTo(menjacnicaGui);
			izvrsiZamenuGui.setVisible(true);
		}
	}

	public static void zatvoriProzorObrisiKursGui() {
		obrisiKursGui.setVisible(false);
		obrisiKursGui = null;

	}

	public static void zatvoriProzorIzrsiZamenuGui() {
		izvrsiZamenuGui.setVisible(false);
		izvrsiZamenuGui = null;
	}

	public static void zatvoriProzorDodajKursGui() {
		dodajKursGui.setVisible(false);
		dodajKursGui = null;
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
}
