package solace;

import org.junit.jupiter.api.Disabled;
import org.pemacy.solace.SolaceApplicationBuilder;

@Disabled
class SolaceApplicationTest {

    public static void main(final String[] args) {
        final var window = new SolaceApplicationBuilder()
                .build()
                .setVisible(true)
                .centerOnScreen();
    }

}
