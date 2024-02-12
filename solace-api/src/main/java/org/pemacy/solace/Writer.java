package org.pemacy.solace;

public interface Writer<Self> {

    Writer<Self> print(final Object content);

    default Writer<Self> println() {
        return print("");
    }

    default Writer<Self> println(final Object content) {
        return print(content + "\n");
    }

    default Writer<Self> printf(final Object format, final Object... args) {
        return print(String.valueOf(format).formatted(args));
    }

    default Writer<Self> printfln(final Object format, final Object... args) {
        return println(String.valueOf(format).formatted(args));
    }

    Self getSelf();

}
