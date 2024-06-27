package utilities.cmd;

import lombok.SneakyThrows;

public class CommandWindows {
    @SneakyThrows
    public static Process execute(String command) {
        return Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"%s\"".formatted(command));
    }
}
