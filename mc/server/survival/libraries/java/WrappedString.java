package mc.server.survival.libraries.java;

import org.jetbrains.annotations.NotNull;

public class WrappedString
{
    private @NotNull String string;

    public WrappedString(@NotNull String string)
    {
        this.string = string;
    }

    public static WrappedString wrapString(@NotNull String string)
    {
        return new WrappedString(string);
    }

    public @NotNull String getString() { return this.string; }

    public @NotNull WrappedString center(final int bukkitCodes, final int hexCodes, final int length)
    {
        String wrappedString = this.string;
        StringBuilder stringBuilder = new StringBuilder(wrappedString);

        final int space = (length - (wrappedString.length() - (hexCodes * 7) - (bukkitCodes * 2))) / 2;

        for (int i = 0; i <= space; i++)
            stringBuilder.insert(0, " ");

        wrappedString = stringBuilder.toString();
        this.string = wrappedString;

        return this;
    }
}