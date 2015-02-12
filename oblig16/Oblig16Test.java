package oblig16;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Oblig16Test {

	@Test
	public void toEuro() {
		assertEquals(Oblig16.getRate("EUR", "NOK") * 10.0, Oblig16.konverterTilEuro(10.0, "NOK"), 0.1);
		assertEquals(Oblig16.getRate("EUR", "USD") * 10.0, Oblig16.konverterTilEuro(10.0, "USD"), 0.1);
		assertEquals(Oblig16.getRate("EUR", "JPY") * 10.0, Oblig16.konverterTilEuro(10.0, "JPY"), 0.1);
	}

	@Test
	public void fromEuro() {
		assertEquals(Oblig16.getRate("NOK", "EUR") * 10.0, Oblig16.konverterFraEuro(10.0, "NOK"), 0.1);
		assertEquals(Oblig16.getRate("USD", "EUR") * 10.0, Oblig16.konverterFraEuro(10.0, "USD"), 0.1);
		assertEquals(Oblig16.getRate("JPY", "EUR") * 10.0, Oblig16.konverterFraEuro(10.0, "JPY"), 0.1);
	}

	@Test
	public void convert() {
		assertEquals(Oblig16.getRate("NOK", "EUR") * 10.0, Oblig16.konverter(10.0, "NOK", "EUR"), 0.1);
		assertEquals(Oblig16.getRate("USD", "JPY") * 10.0, Oblig16.konverter(10.0, "USD", "JPY"), 0.1);
		assertEquals(Oblig16.getRate("EUR", "JPY") * 10.0, Oblig16.konverter(10.0, "EUR", "JPY"), 0.1);
	}
}
