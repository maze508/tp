package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OrderTest {
    private static final String VALID_DATE = "2020-01-01";
    private static final String VALID_REMARK = "100 chicken wings";

    private static final String INVALID_DATE = "2020-99-99";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Order(null, null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Order(new Date(INVALID_DATE), VALID_REMARK));
    }

    @Test
    public void constructor_order_success() {
        Order order = new Order(new Date(VALID_DATE), VALID_REMARK);
        assertEquals(VALID_DATE, order.getDate().toString());
        assertEquals(VALID_REMARK, order.getRemark());
        assertEquals("Pending", order.getStatus());

        Order orderWithStatus = new Order(new Date(VALID_DATE), VALID_REMARK, "Delivered");
        assertEquals(VALID_DATE, orderWithStatus.getDate().toString());
        assertEquals(VALID_REMARK, orderWithStatus.getRemark());
        assertEquals("Delivered", orderWithStatus.getStatus());
    }

    @Test
    public void equals() {
        Order order = new Order(new Date(VALID_DATE), VALID_REMARK);

        // same object -> returns true
        assertTrue(order.equals(order));

        // same values -> returns true
        Order orderCopy = new Order(order.getDate(), order.getRemark(), order.getStatus());
        assertTrue(order.equals(orderCopy));

        // different types -> returns false
        assertFalse(order.equals(1));

        // null -> returns false
        assertFalse(order.equals(null));

        // different Order -> returns false
        Order differentOrder = new Order(order.getDate(), "200 chicken wings");
        assertFalse(order.equals(differentOrder));
    }

    @Test
    public void hashcode() {
        Order order = new Order(new Date(VALID_DATE), VALID_REMARK);

        // same order -> returns same hashcode
        assertEquals(order.hashCode(), new Order(new Date(VALID_DATE), VALID_REMARK).hashCode());

        // different date -> returns different hashcode
        assertNotEquals(order.hashCode(), new Order(new Date("2022-01-01"), VALID_REMARK).hashCode());

        // different remark -> returns different hashcode
        assertNotEquals(order.hashCode(), new Order(new Date(VALID_DATE), "20 chicken wings").hashCode());
    }
}
