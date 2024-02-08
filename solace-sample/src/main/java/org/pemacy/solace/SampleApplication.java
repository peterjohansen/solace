package org.pemacy.solace;

public class SampleApplication {

    public static void main(final String[] args) {
        final var window = new SolaceApplicationBuilder()
                .title("Solace Sample Application")
                .build();
    }

}
