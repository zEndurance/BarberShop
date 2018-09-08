package unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Booking;

public class BookingTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private String[] bookingOneValues;
	private Booking bookingOne;

	@Before
	public void setUp() throws Exception {
		bookingOneValues = new String[]{};
	}

	@Test
	public void throwsIllegalArgumentExceptionOnEmptyBookingConstructor() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid Booking Values!");
		// This should throw an exception
		bookingOne = new Booking(bookingOneValues);
	}

}
